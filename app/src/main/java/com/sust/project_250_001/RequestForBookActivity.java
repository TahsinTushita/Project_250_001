package com.sust.project_250_001;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestForBookActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DatabaseReference database;

    private EditText TitleText,AuthorText;
    private Button SubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_book);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);



        TitleText = findViewById(R.id.idBookName);
        AuthorText = findViewById(R.id.idBookAuthor);
        SubmitBtn = findViewById(R.id.idBookReq);

        SubmitBtn.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title,author;
            title = TitleText.getText().toString().trim();
            author = AuthorText.getText().toString().trim();
            if(title.isEmpty()==false) {
                database = FirebaseDatabase.getInstance().getReference("Requests").push();
                database.child("title").setValue(title);
                database.child("author").setValue(author);
                onBackPressed();
            } else
                Snackbar.make(view.getRootView(),"Please specify a valid book name",Snackbar.LENGTH_LONG).show();
        }
    };
}
