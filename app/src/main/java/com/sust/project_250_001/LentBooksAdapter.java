package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LentBooksAdapter extends RecyclerView.Adapter<LentBooksAdapter.LentBooksHolder> {

    public interface OnItemClickListener {
        void onItemClick(LentBooksAdapter lentBooksAdapter);
    }

    private Context context;
    private ArrayList<Request> lentBooks = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private OnItemClickListener listener;

    public LentBooksAdapter(Context context, ArrayList<Request> lentBooks, FirebaseDatabase database, OnItemClickListener listener) {
        this.context = context;
        this.lentBooks = lentBooks;
        this.database = database;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LentBooksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.lentbooks_recyclerview,viewGroup,false);
        return new LentBooksAdapter.LentBooksHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull LentBooksHolder lentBooksHolder, int i) {

        lentBooksHolder.setDetails(lentBooks.get(i));
        lentBooksHolder.bind(lentBooks.get(i),listener);
        lentBooksHolder.setVisibility(lentBooks.get(i));
    }

    @Override
    public int getItemCount() {
        return lentBooks.size();
    }

    public class LentBooksHolder extends RecyclerView.ViewHolder {

        private TextView lentBook,lentTo,status;

        public LentBooksHolder(@NonNull View itemView) {
            super(itemView);
            lentBook = itemView.findViewById(R.id.bookTitle);
            lentTo = itemView.findViewById(R.id.userName);
            status = itemView.findViewById(R.id.statusid);
        }

        public void setDetails(Request request){

            lentBook.setText(request.getBookTitle());
            lentTo.setText(request.getUsername());

            if(request.getStatus()==0)
                status.setText("borrowed");
            else if(request.getStatus()==1)
                status.setText("returned");
        }

        public void bind(final Request request,OnItemClickListener listener){
            itemView.findViewById(R.id.btnReturned).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lentBooks.remove(request);
                    notifyItemRemoved(getAdapterPosition());

                    DatabaseReference databaseReference = database.getReference("Profile/"+LoginActivity.user+
                                                "/booklist/"+request.getParent()+"/requests/"+request.getUsername());
                    databaseReference.setValue(null);

                    databaseReference = database.getReference("Profile/"+request.getUsername()+"/requestedBooks/"+
                                            request.getParent());
                    databaseReference.setValue(null);

                    databaseReference = database.getReference("Profile/"+LoginActivity.user+"/lentBooks/"+request.getParent());
                    databaseReference.setValue(null);

                    databaseReference = database.getReference("Profile/"+request.getUsername()+"/borrowedBooks/"+
                                                request.getParent());
                    databaseReference.setValue(null);

                    databaseReference = database.getReference("Profile/"+LoginActivity.user+"/booklist/"+
                            request.getParent());
                    databaseReference.child("availability").setValue(1);

                    databaseReference = database.getReference("Books/"+request.getParent()+"/users/"+LoginActivity.user);
                    databaseReference.child("availability").setValue(1);
                }
            });
        }

        public void setVisibility(Request request){
            if(request.getStatus()==1){
                itemView.findViewById(R.id.btnReturned).setVisibility(View.VISIBLE);
            }
        }

    }
}
