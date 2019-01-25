package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.BorrowedBooksHolder> {

    public interface OnItemClickListener {
        void onItemClick(BorrowedBooksAdapter borrowedBooksAdapter);
    }

    private Context context;
    private ArrayList<Request> borrowedBooks = new ArrayList<>();
    private FirebaseDatabase database;
    private BorrowedBooksAdapter.OnItemClickListener listener;

    public BorrowedBooksAdapter(Context context, ArrayList<Request> borrowedBooks, FirebaseDatabase database, OnItemClickListener listener) {
        this.context = context;
        this.borrowedBooks = borrowedBooks;
        this.database = database;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BorrowedBooksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.borrowedbooks_recyclerview,viewGroup,false);
        return new BorrowedBooksAdapter.BorrowedBooksHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBooksHolder borrowedBooksHolder, int i) {

        borrowedBooksHolder.setDetails(borrowedBooks.get(i));
        borrowedBooksHolder.bind(borrowedBooks.get(i),listener);
        borrowedBooksHolder.setVisibility(borrowedBooks.get(i));
    }

    @Override
    public int getItemCount() {
        return borrowedBooks.size();
    }

    public class BorrowedBooksHolder extends RecyclerView.ViewHolder {

        private TextView borrowedBook,borrowedFrom,status;

        public BorrowedBooksHolder(@NonNull View itemView) {
            super(itemView);
            borrowedBook = itemView.findViewById(R.id.bookTitle);
            borrowedFrom = itemView.findViewById(R.id.userName);
            status = itemView.findViewById(R.id.statusid);
        }

        public void setDetails(Request request){
            borrowedBook.setText(request.getBookTitle());
            borrowedFrom.setText(request.getUsername());
            if(request.getStatus()==0) {
                status.setText("borrowed");
                status.setTextColor(ContextCompat.getColor(context,android.R.color.holo_orange_dark));
            }
            else if(request.getStatus()==1) {
                status.setText("returned");
                status.setTextColor(ContextCompat.getColor(context,android.R.color.holo_blue_bright));
            }
        }

        public void bind(final Request request,OnItemClickListener listener){
            itemView.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference db = database.getReference("Profile/"+LoginActivity.user+"/borrowedBooks/"+
                                                    request.getParent());
                    db.child("status").setValue(1);

                    db = database.getReference("Profile/"+request.getUsername()+"/lentBooks/"+request.getParent());
                    db.child("status").setValue(1);

                    request.setStatus(1);
                    setVisibility(request);
                    setDetails(request);

                }
            });
        }

        public void setVisibility(Request request){
            if(request.getStatus()==1){
                itemView.findViewById(R.id.btnReturn).setVisibility(View.GONE);
            }
        }
    }
}
