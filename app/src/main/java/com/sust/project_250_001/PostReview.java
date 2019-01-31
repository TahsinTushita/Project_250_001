package com.sust.project_250_001;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostReview extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText reviewTitle,reviewDesc;
    private Button postBtn;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;

    final String EXTRA_BOOKPARENT = "bookParent";
    private Book book;
    private String bookParent;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reviewTitle = findViewById(R.id.reviewTitle) ;
        reviewDesc = findViewById(R.id.reviewDesc);
        postBtn = findViewById(R.id.postBtn);

        progressDialog = new ProgressDialog(this);
        book = (Book) getIntent().getExtras().getSerializable(EXTRA_BOOKPARENT);
        bookParent = book.getParent();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child(bookParent).child("reviews");


        userName = "Anonymous";

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        userName = firebaseUser.getEmail();
        userName = userName.substring(0,userName.lastIndexOf('@'));

    }

    public void startPosting(View view) {
        String postTitle = reviewTitle.getText().toString();
        String postDesc = reviewDesc.getText().toString();
        if(postDesc.isEmpty()) {
            Snackbar.make(view,"I Can't post Anything!! Your message is John Cena",Snackbar.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Posting....");
        progressDialog.show();

        DatabaseReference newPost;
        newPost = mDatabaseReference.child(userName);
        newPost.child("postTitle").setValue(postTitle);
        newPost.child("postDesc").setValue(postDesc);
        newPost.child("username").setValue(userName);

        DatabaseReference recentReviewPost = FirebaseDatabase.getInstance().getReference().child("Reviews").push();
        recentReviewPost.child("bookTitle").setValue(book.getTitle());
        recentReviewPost.child("userName").setValue(userName);
        recentReviewPost.child("reviewText").setValue(postDesc);
        progressDialog.dismiss();

        onBackPressed();

    }
}
