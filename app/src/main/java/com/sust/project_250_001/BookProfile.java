package com.sust.project_250_001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookProfile extends AppCompatActivity implements View.OnClickListener{

    private Book book;
    public static final String EXTRA_BOOK = "bookObject";

    private ImageView bookCover;
    private TextView bookAuthor;
    private TextView bookTitle;
    private TextView availability;
    private Button booklistbtn,wishlishbtn;

    private TextView popupReview;

    private RecyclerView reviewView;
    private BookReviewAdapter reviewAdapter;
    private ArrayList<BookReview> reviewArrayList;
    private DatabaseReference reviewDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);
        book = (Book) getIntent().getExtras().getSerializable(EXTRA_BOOK);

        bookCover = findViewById(R.id.bookCover);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        availability = findViewById(R.id.checkAvailability);
        booklistbtn = findViewById(R.id.bookListid);
        wishlishbtn = findViewById(R.id.wishListid);

        availability.setOnClickListener(this);
        booklistbtn.setOnClickListener(this);
        wishlishbtn.setOnClickListener(this);

        Picasso.get().load(book.getImgurl()).into(bookCover);

        bookAuthor.setText(book.getAuthor());
        bookTitle.setText(book.getTitle());


        reviewDatabase = FirebaseDatabase.getInstance().getReference("Books").child(book.getParent()).child("reviews");
        //Recent Reviews
        reviewView = findViewById(R.id.reviewView);
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewArrayList = new ArrayList<>();
        reviewAdapter = new BookReviewAdapter(BookProfile.this, reviewArrayList,listener);
        reviewView.setAdapter(reviewAdapter);


//        Recent Reviews fetching from firebase
        reviewDatabase.addValueEventListener(reviewValueEventListener);


    }


    ValueEventListener reviewValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                reviewArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookReview review = snapshot.getValue(BookReview.class);
                    reviewArrayList.add(review);
                }
                BookReviewAdapter adapter = new BookReviewAdapter(BookProfile.this,reviewArrayList,listener);
                reviewView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else return;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void startPosting(View view) {
        Intent intent = new Intent(this,PostReview.class);
        intent.putExtra("bookParent",book);
        startActivity(intent);
    }

    BookReviewAdapter.OnItemClickListener listener = new BookReviewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BookReview book) {

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);

            popupReview = popupView.findViewById(R.id.popupReview);
            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupReview.setText(book.getPostDesc());
            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
//            startActivity(new Intent(BookProfile.this,Profile.class));
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id==R.id.bookListid)
            Toast.makeText(BookProfile.this,"Added to your Book list",Toast.LENGTH_SHORT).show();

        if(id==R.id.wishListid)
            Toast.makeText(BookProfile.this,"Added to your Wish list",Toast.LENGTH_SHORT).show();

        if(id==R.id.checkAvailability){
            Intent intent = new Intent(BookProfile.this,MapActivity.class);
            startActivity(intent);
        }
    }
}
