package com.sust.project_250_001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookProfile extends AppCompatActivity {

    private Book book;
    public static final String EXTRA_BOOK = "bookObject";

    private ImageView bookCover;
    private TextView bookAuthor;
    private TextView bookTitle;





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

        bookAuthor.setText(book.getAuthor());
        bookTitle.setText(book.getTitle());



        //Recent Reviews
        reviewView = findViewById(R.id.reviewView);
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewArrayList = new ArrayList<>();
        reviewAdapter = new BookReviewAdapter(BookProfile.this,reviewArrayList);
        reviewView.setAdapter(reviewAdapter);


        //Recent Reviews fetching from firebase
        reviewDatabase = FirebaseDatabase.getInstance().getReference("Books").child(book.getParent()).child("reviews");
        reviewDatabase.addListenerForSingleValueEvent(reviewValueEventListener);

    }


    ValueEventListener reviewValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            reviewArrayList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookReview review = snapshot.getValue(BookReview.class);
                    reviewArrayList.add(review);
                }
                BookReviewAdapter adapter = new BookReviewAdapter(BookProfile.this,reviewArrayList);
                reviewView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void startPosting(View view) {
        Intent intent = new Intent(this,PostReview.class);
        intent.putExtra("bookParent",book.getParent());
        startActivity(intent);
    }
}
