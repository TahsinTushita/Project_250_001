package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LentBooks extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private LentBooksAdapter adapter;
    private FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
    LentBooksAdapter.OnItemClickListener listener;

    private ArrayList<Request> lentBooksList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_books);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lent Books");

        recyclerView = findViewById(R.id.lentbooks_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LentBooksAdapter(this,lentBooksList,databaseReference,listener);
        recyclerView.setAdapter(adapter);

        fetchBooks();
    }

    private void fetchBooks() {
        databaseReference.getReference("Profile/"+LoginActivity.user+"/lentBooks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Request request = dataSnapshot.getValue(Request.class);
                System.out.println("BOokTitle " + request.getBookTitle());
                lentBooksList.add(request);
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
}
