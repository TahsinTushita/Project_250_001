package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private TextView userName;
    private FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
    RequestAdapter.OnItemClickListener listener;

    private ArrayList<Request> requestList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Requests");

        userName = findViewById(R.id.userName);
        recyclerView = findViewById(R.id.request_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RequestAdapter(this,requestList, listener,databaseReference);
        recyclerView.setAdapter(adapter);

        fetchBooks();
    }

    private void fetchRequests(final DatabaseReference database){
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request = dataSnapshot.getValue(Request.class);
                requestList.add(request);
                adapter.notifyDataSetChanged();
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
    }

    private void fetchBooks() {
        databaseReference.getReference("Profile/"+LoginActivity.user+"/booklist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Book book = dataSnapshot.getValue(Book.class);
                fetchRequests(databaseReference.getReference("Profile/"+LoginActivity.user+"/booklist/"+book.getParent()+"/requests"));
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
    }
}