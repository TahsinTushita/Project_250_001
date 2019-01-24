package com.sust.project_250_001;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lapism.searchview.Search;
import com.lapism.searchview.database.SearchHistoryTable;
import com.lapism.searchview.widget.SearchAdapter;
import com.lapism.searchview.widget.SearchItem;
import com.lapism.searchview.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Add the toolbar;
    private Toolbar toolbar;
    private ArrayList<String> trendingList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView reviewView;
    private BookAdapter adapter;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Book> bookArrayList;
    private ArrayList<Review> reviewArrayList;
    public static ArrayList<ProfileInfo> profileInfoArrayList;
    private DatabaseReference database,reviewDatabase,userDatabase;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private TextView drawerUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Set toolbar as actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //Trending books
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        bookArrayList = new ArrayList<>();
        adapter = new BookAdapter(this,bookArrayList,listener);
        recyclerView.setAdapter(adapter);

        profileInfoArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        //Recent Reviews
        reviewView = findViewById(R.id.reviewView);
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewArrayList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this,reviewArrayList);
        reviewView.setAdapter(reviewAdapter);

        //Trending books fetching from firebase
        database = FirebaseDatabase.getInstance().getReference("Books");

        //Recent Reviews fetching from firebase
        reviewDatabase = FirebaseDatabase.getInstance().getReference("Reviews");
        reviewDatabase.addValueEventListener(reviewValueEventListener);


        SearchItem suggestion = new SearchItem(this);
        suggestion.setTitle("Title");
        suggestion.setIcon1Resource(R.drawable.search_ic_search_black_24dp);
        suggestion.setSubtitle("Subtitle");

        List<SearchItem> suggestions = new ArrayList<>();
        suggestions.add(suggestion);

        final SearchHistoryTable mHistoryDatabase = new SearchHistoryTable(this);


        final SearchView searchView = findViewById( R.id.searchBar);
        searchView.setLogoIcon(R.drawable.book_search);
        searchView.setLogoColor(getResources().getColor(R.color.colorSearch));
        searchView.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                SearchItem item = new SearchItem(HomePageActivity.this);
                item.setTitle(query);
                mHistoryDatabase.addItem(item);

                Intent intent = new Intent(HomePageActivity.this,SearchResults.class);
                intent.putExtra("searchText",query.toString());
                startActivity(intent);
                return true;
            }

            @Override
            public void onQueryTextChange(CharSequence newText) {

            }


        });

        SearchAdapter searchAdapter = new SearchAdapter(this);
        searchAdapter.setSuggestionsList(suggestions);
        searchAdapter.setOnSearchItemClickListener(new SearchAdapter.OnSearchItemClickListener() {
            @Override
            public void onSearchItemClick(int position, CharSequence title, CharSequence subtitle) {
                SearchItem item = new SearchItem(HomePageActivity.this);
                item.setTitle(title);
                item.setSubtitle(subtitle);
                mHistoryDatabase.addItem(item);

                Intent intent = new Intent(HomePageActivity.this,SearchResults.class);
                intent.putExtra("searchText",title.toString());
                startActivity(intent);
            }
        });

        searchView.setAdapter(searchAdapter);

        Query database = FirebaseDatabase.getInstance().getReference("TopBooks").orderByChild("count");
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(dataSnapshot.getKey());
                Query query = FirebaseDatabase.getInstance().getReference("Books");
                query.orderByChild("parent").equalTo(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Book book = snapshot.getValue(Book.class);
                                if(bookArrayList.contains(book)==false)
                                bookArrayList.add(0,book);
                            }
                            BookAdapter adapter = new BookAdapter(HomePageActivity.this,bookArrayList,listener);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
        Snackbar.make(findViewById(android.R.id.content),"Welcome " + LoginActivity.user.toUpperCase(),Snackbar.LENGTH_LONG).show();
    }

        ValueEventListener reviewValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            reviewArrayList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    reviewArrayList.add(0,review);
                }
                ReviewAdapter adapter = new ReviewAdapter(HomePageActivity.this,reviewArrayList);
                reviewView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if(id==R.id.idLogout) {
                firebaseAuth.signOut();
                if (firebaseAuth.getCurrentUser() == null)
                    startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            }

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createListData() {
        adapter.notifyDataSetChanged();
    }

    BookAdapter.OnItemClickListener listener = new BookAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Book book) {
            startActivity(new Intent(HomePageActivity.this,BookProfile.class).putExtra("bookObject",book));
        }
    };



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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        Intent intent = null;

        switch(id) {

            case R.id.profileid:
                intent = new Intent(this, Profile.class);
                intent.putExtra("profileID", LoginActivity.user);
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
                intent = new Intent(this,HomePageActivity.class);
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
        this.startActivity(intent);
        return false;
    }

}
