package com.sust.project_250_001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private TextView drawerUserName;
    private DatabaseReference booklist;

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private SearchresultsAdapter searchresultsAdapter;
    private ArrayList<Book> bookArrayList;

    private ArrayList<String> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book List");

        drawer = (DrawerLayout) findViewById(R.id.drawerid);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_id);
        drawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.nav_open,R.string.nav_close);
        drawerUserName = navigationView.getHeaderView(0).findViewById(R.id.navuserid);
        String user = LoginActivity.user.toUpperCase();
        drawerUserName.setText(user);

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        bookList = new ArrayList<>();

        recyclerView = findViewById(R.id.search_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        bookArrayList = new ArrayList<>();
        searchresultsAdapter = new SearchresultsAdapter(this, bookArrayList,listener);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Profile").child(LoginActivity.user).child("booklist");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    bookList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String book = (String) snapshot.getValue(Book.class).getParent();
                            bookList.add(book);
                            System.out.println(book);
                         }
                    }
                }
                updateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(searchresultsAdapter);

    }

    private void updateRecyclerView() {
//        bookArrayList.clear();
        for (String st : bookList) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Books").child(st);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Book book1 = dataSnapshot.getValue(Book.class);
                    if(book1!=null) {
                        bookArrayList.add(book1);
                        System.out.println(book1.getAuthor());
                    }
                    searchresultsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerid);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }

        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        Intent intent = null;

        switch(id) {

            case R.id.profileid:
                intent = new Intent(this, Profile.class);
                intent.putExtra("profileID",LoginActivity.user);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.bookListid:
                intent = new Intent(this, BookList.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.wishListid:
                intent = new Intent(this, WishList.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.homePage:
                intent = new Intent(this, HomePageActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

        }
        startActivity(intent);
        return true;
    }

    SearchresultsAdapter.OnItemClickListener listener = new SearchresultsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Book book) {
            Intent i = new Intent(BookList.this,BookProfile.class);
            i.putExtra("bookObject",book);
            startActivity(i);
        }
    };

}
