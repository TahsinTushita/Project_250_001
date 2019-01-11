package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private TextView bookTitle;
    private TextView userName;
    private TextView reviewText;
    private CardView cardView;

    public class ReviewHolder extends RecyclerView.ViewHolder {
        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            userName = itemView.findViewById(R.id.userName);
            reviewText = itemView.findViewById(R.id.reviewText);
            reviewText.setMaxLines(5);
        }
        public void setDetails(Review review) {
            bookTitle.setText(review.getBookTitle());
            reviewText.setText(review.getReviewText());
            userName.setText(review.getUserName());
        }
    }

    private Context context;
    private ArrayList<Review> reviewArrayList;
    public ReviewAdapter(Context context,ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_review_row,viewGroup,false);
//        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_review_row,viewGroup,false);
        return new ReviewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int i) {
        Review mReview = reviewArrayList.get(i);
        reviewHolder.setDetails(mReview);
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

}
