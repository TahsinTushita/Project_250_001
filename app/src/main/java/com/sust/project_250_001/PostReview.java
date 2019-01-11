package com.sust.project_250_001;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostReview extends AppCompatActivity {

    private EditText reviewTitle,reviewDesc;
    private Button postBtn;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;

    final String EXTRA_BOOKPARENT = "bookParent";
    private String bookParent;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        reviewTitle = findViewById(R.id.reviewTitle) ;
        reviewDesc = findViewById(R.id.reviewDesc);
        postBtn = findViewById(R.id.postBtn);

        progressDialog = new ProgressDialog(this);
        bookParent = (String) getIntent().getExtras().get(EXTRA_BOOKPARENT);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child(bookParent).child("reviews");


        userName = "Anonymous";

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        userName = firebaseUser.getEmail();
        userName = userName.substring(userName.lastIndexOf('@'));

    }

    public void startPosting(View view) {
        String postTitle = reviewTitle.getText().toString();
        String postDesc = reviewDesc.getText().toString();

        progressDialog.setMessage("Posting....");
        progressDialog.show();

        DatabaseReference newPost;
        newPost = mDatabaseReference.child(userName.toString());
        newPost.child("postTitle").setValue(postTitle);
        newPost.child("postDesc").setValue(postDesc);
        newPost.child("username").setValue(userName);

        progressDialog.dismiss();

    }
}
