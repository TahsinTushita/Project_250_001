package com.sust.project_250_001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView drawerUserName;
    private SearchresultsAdapter searchresultsAdapter;
    private ArrayList<Book> bookArrayList;
    private ArrayList<String> wishList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wish List");
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
        wishList = new ArrayList<>();
        recyclerView = findViewById(R.id.search_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        bookArrayList = new ArrayList<>();
        searchresultsAdapter = new SearchresultsAdapter(this, bookArrayList,listener);
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Profile").child(LoginActivity.user).child("wishlist");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wishList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.exists()) {
                        String book = (String) postSnapshot.getValue(Book.class).getParent();
                        wishList.add(book);
                        System.out.println("Book Name " + book);
                    }
                }

                updateRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateRecyclerView() {
        bookArrayList.clear();
        for (String st : wishList) {
            Query db = FirebaseDatabase.getInstance().getReference("Books").orderByChild("parent").equalTo(st);
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    bookArrayList.add(dataSnapshot.getValue(Book.class));
                    recyclerView.setAdapter(searchresultsAdapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            searchresultsAdapter = new SearchresultsAdapter(this, bookArrayList,listener);
            recyclerView.setAdapter(searchresultsAdapter);
            searchresultsAdapter.notifyDataSetChanged();
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

            case R.id.updProfile:
                intent = new Intent(this,UpdateProfile.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.requestsid:
                intent = new Intent(this,RequestsActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.requestedBooksid:
                intent = new Intent(this,RequestedBooksActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.borrowedBooksid:
                intent = new Intent(this,BorrowedBooks.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.lentBooksid:
                intent = new Intent(this,LentBooks.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.allBooks:
                intent = new Intent(this,AllBooksActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.requestForBook:
                intent = new Intent(this,RequestForBookActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                break;

        }
        startActivity(intent);
        return true;
    }

    SearchresultsAdapter.OnItemClickListener listener = new SearchresultsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Book book) {
            Intent i = new Intent(WishList.this,BookProfile.class);
            i.putExtra("bookObject",book);
            startActivity(i);
        }
    };

}
