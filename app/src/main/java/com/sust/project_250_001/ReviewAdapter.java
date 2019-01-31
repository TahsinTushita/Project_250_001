package com.sust.project_250_001;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Review review);
    }

    private TextView bookTitle;
    private TextView userName;
    private TextView reviewText;

    public class ReviewHolder extends RecyclerView.ViewHolder {

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            userName = itemView.findViewById(R.id.userName);
            reviewText = itemView.findViewById(R.id.reviewText);
        }
        public void setDetails(Review review) {
            bookTitle.setText(review.getBookTitle());
            reviewText.setText(review.getReviewText());
            userName.setText(review.getUserName());
        }
        public void bind(final Review review,final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.setMessage("Please Wait...");
                    dialog.show();
//                    Snackbar.make(view,"Item Clicked!!",Snackbar.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("Books").orderByChild("title").equalTo(review.getBookTitle())
                            .addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    context.startActivity(new Intent(context.getApplicationContext(),BookProfile.class).putExtra("bookObject",dataSnapshot.getValue(Book.class)));
                                    dialog.dismiss();
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
            });
        }
    }

    private ProgressDialog dialog;
    private Context context;
    private ArrayList<Review> reviewArrayList;
    private OnItemClickListener listener;

    public ReviewAdapter(Context context,ArrayList<Review> reviewArrayList,OnItemClickListener listener) {
        this.reviewArrayList = reviewArrayList;
        this.context = context;
        this.listener = listener;
        dialog = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_review_row,viewGroup,false);
        return new ReviewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int i) {
        Review mReview = reviewArrayList.get(i);
        reviewHolder.setDetails(mReview);
        reviewHolder.bind(mReview,listener);
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

}
