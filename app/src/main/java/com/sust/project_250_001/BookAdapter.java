package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public class BookHolder extends RecyclerView.ViewHolder{

        private TextView bookTitle,bookAuthor,bookISBN;
        private ImageView imgurl;
        private CardView cardView;

        public BookHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            //bookAuthor = itemView.findViewById(R.id.bookAuthor);
            imgurl = itemView.findViewById(R.id.imgurl);
        }

        public void setDetails(Book book) {
            bookTitle.setText(book.getTitle());
            //bookAuthor.setText(book.getAuthor());
            //bookISBN.setText(book.getIsbn());
            Picasso.get().load(book.getImgurl()).into(imgurl);
        }

        public void bind(final Book book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });

        }
    }

    private Context context;
    private final OnItemClickListener listener;

    private ArrayList<Book> books;
    public BookAdapter(Context context, ArrayList<Book> books, OnItemClickListener listener) {
        this.context = context;
        this.books = books;
        this.listener = listener;
    }


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row,viewGroup,false);
        return new BookHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder book, int i) {
        Book mBook = books.get(i);
        book.setDetails(mBook);
        book.bind(books.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
