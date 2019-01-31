package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestedBooksActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private RequestedBooksAdapter adapter;
    private FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
    RequestedBooksAdapter.OnItemClickListener listener;

    private ArrayList<Request> requestedBooksList = new ArrayList();
    private TextView noRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_books);

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

        recyclerView = findViewById(R.id.requestedbooks_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RequestedBooksAdapter(this,requestedBooksList, listener,databaseReference);
        recyclerView.setAdapter(adapter);

        noRequest = findViewById(R.id.noRequestid);

        fetchBooks();

    }


    private void fetchBooks(){
        databaseReference.getReference("Profile/"+LoginActivity.user+"/requestedBooks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request = dataSnapshot.getValue(Request.class);
                System.out.println("BOokTitle " + request.getBookTitle());
                requestedBooksList.add(request);
                adapter.notifyDataSetChanged();
                if(requestedBooksList.size() != 0) noRequest.setVisibility(View.GONE);
                else noRequest.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(requestedBooksList.size() != 0) noRequest.setVisibility(View.GONE);
                else noRequest.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
