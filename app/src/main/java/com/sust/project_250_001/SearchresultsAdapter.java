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

public class SearchresultsAdapter extends RecyclerView.Adapter<SearchresultsAdapter.SearchresultsHolder> {

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }


    private Context context;
    private ArrayList<Book> books;
    private final OnItemClickListener listener;

    @NonNull
    @Override
    public SearchresultsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_recyclerview,viewGroup,false);
        return new SearchresultsAdapter.SearchresultsHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchresultsHolder searchresults, int i) {
        Book mBook = books.get(i);
        searchresults.setDetails(mBook);
        searchresults.bind(books.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class SearchresultsHolder extends RecyclerView.ViewHolder {

        private TextView bookTitle,bookAuthor,bookISBN;
        private ImageView imgurl;
        private CardView cardView;


        public SearchresultsHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookISBN = itemView.findViewById(R.id.bookISBN);
            imgurl = itemView.findViewById(R.id.bookCover);
        }

        public void setDetails(Book book){
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookISBN.setText(book.getIsbn());
            Picasso.get().load(book.getImgurl()).into(imgurl);
        }

        public void bind(final Book book, final SearchresultsAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });

        }
    }

    public SearchresultsAdapter(Context context,ArrayList<Book> books,OnItemClickListener listener){
        this.context = context;
        this.books = books;
        this.listener = listener;
    }


}
