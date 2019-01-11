package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookReviewAdapter extends RecyclerView.Adapter<BookReviewAdapter.BookReviewHolder> {

    public class BookReviewHolder extends RecyclerView.ViewHolder{

        private TextView username,posttitle,postdesc;
        private CardView cardView;

        public BookReviewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.booksReviewCardView);
            username = itemView.findViewById(R.id.userName);
            posttitle = itemView.findViewById(R.id.bookTitle);
            postdesc = itemView.findViewById(R.id.bookPost);
        }

        public void setDetails(BookReview bookReview) {
            username.setText(bookReview.getUsername());
            posttitle.setText(bookReview.getPostTitle());
            postdesc.setText(bookReview.getPostDesc());
        }

    }

    private Context context;

    private ArrayList<BookReview> booksReviews;
    public BookReviewAdapter(Context context, ArrayList<BookReview> booksReviews) {
        this.context = context;
        this.booksReviews = booksReviews;
    }


    @NonNull
    @Override
    public BookReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_reviews,viewGroup,false);
        return new BookReviewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookReviewHolder book, int i) {
        BookReview mBook = booksReviews.get(i);
        book.setDetails(mBook);
    }

    @Override
    public int getItemCount() {
        return booksReviews.size();
    }
}
