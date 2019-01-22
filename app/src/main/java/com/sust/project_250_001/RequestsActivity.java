package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();

    private ArrayList requestList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        recyclerView = findViewById(R.id.request_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RequestAdapter(this,requestList);

        recyclerView.setAdapter(adapter);

        fetchRequest();
    }

    private void fetchRequest() {

        final DatabaseReference database = databaseReference.getReference("Profile/"+LoginActivity.user+"/requestedBooks");
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request = dataSnapshot.getValue(Request.class);
                requestList.add(request);
                adapter.notifyDataSetChanged();
                System.out.println("Book Title " + request.getBookTitle());
                System.out.println("UserName " + request.getUsername());
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
