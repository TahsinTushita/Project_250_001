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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
            imgurl = itemView.findViewById(R.id.bookCover);
        }

        public void setDetails(Book book){
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            Picasso.get().load(book.getImgurl()).into(imgurl);
        }

        void removeAt(int position)
        {
            books.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,books.size());
        }

        void removeFromBooklistDatabase(Book book){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile")
                    .child(LoginActivity.user)
                    .child("booklist")
                    .child(book.getParent());
            databaseReference.setValue(null);

            DatabaseReference database = FirebaseDatabase.getInstance().getReference("Books")
                    .child(book.getParent()).child("users").child(LoginActivity.user);
            database.setValue(null);
        }

        void removeFromWishlistDatabase(Book book){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile")
                    .child(LoginActivity.user)
                    .child("wishlist")
                    .child(book.getParent());
            databaseReference.setValue(null);
        }

        public void bind(final Book book, final SearchresultsAdapter.OnItemClickListener listener) {
            if(context instanceof SearchResults || context instanceof AllBooksActivity) itemView.findViewById(R.id.btnRemove).setVisibility(View.GONE);
            else {
                itemView.findViewById(R.id.btnRemove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeAt(getAdapterPosition());
                        removeFromBooklistDatabase(book);
                        removeFromWishlistDatabase(book);
                        Toast.makeText(v.getContext(), "Book Has Been Removed", Toast.LENGTH_LONG).show();
                    }
                });
            }
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
