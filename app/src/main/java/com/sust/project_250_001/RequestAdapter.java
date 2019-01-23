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
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {


    public interface OnItemClickListener {
        void onItemClick(RequestAdapter requestAdapter);
    }

    private Context context;
    private ArrayList<Request> requestArrayList;
    private final RequestAdapter.OnItemClickListener listener;
    private FirebaseDatabase database;

    public RequestAdapter(Context context, ArrayList<Request> requestArrayList, OnItemClickListener listener, FirebaseDatabase databaseReference) {
        this.context = context;
        this.requestArrayList = requestArrayList;
        this.listener = listener;
        database = databaseReference;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.request_recyclerview,viewGroup,false);
        return new RequestAdapter.RequestHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder requestHolder, int i) {
        requestHolder.setDetails(requestArrayList.get(i));
        requestHolder.bind(requestArrayList.get(i),listener);
        requestHolder.setVisibility(requestArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder {

        private TextView username,bookTitle,status;


        public RequestHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            status = itemView.findViewById(R.id.statusid);
        }

        public void setDetails(Request request){
            bookTitle.setText(request.getBookTitle());
            username.setText(request.getUsername());
            if(request.getStatus()==0){
                status.setText("pending");
            }
            else if(request.getStatus()==1){
                status.setText("approved");
            }
            else if(request.getStatus()==2)
                status.setText("confirm sent");
            else if(request.getStatus()==3)
                status.setText("confirm recieved");
        }

        public void bind(final Request request, OnItemClickListener listener) {

            itemView.findViewById(R.id.btnApprove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference db = database.getReference("Profile/"+LoginActivity.user
                            +"/booklist/"+request.getParent()
                            +"/requests/"+request.getUsername());
                    db.child("status").setValue(1); //0 for pending,1 for approved,2 for confirmed

                    db = database.getReference("Profile/"+request.getUsername()+"/requestedBooks/"+request.getParent());
                    db.child("status").setValue(1);


                    request.setStatus(1);
                    setVisibility(request);
                    setDetails(request);
                }
            });

            itemView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestArrayList.remove(request);
                    notifyItemRemoved(getAdapterPosition());
                    DatabaseReference db = database.getReference("Profile/"+LoginActivity.user
                            +"/booklist/"+request.getParent()
                            +"/requests/"+request.getUsername());
                    db.setValue(null); //0 for pending,1 for approved,2 for confirmed

                    db = database.getReference("Profile/"+request.getUsername()
                            +"/requestedBooks/"+request.getParent());
                    db.setValue(null); //0 for pending,1 for approved,2 for confirmed


                }
            });

            itemView.findViewById(R.id.btnConfirmSent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference db = database.getReference("Profile/"+LoginActivity.user
                            +"/booklist/"+request.getParent()
                            +"/requests/"+request.getUsername());
                    db.child("status").setValue(2); //0 for pending,1 for approved,2 for confirmed

                    db = database.getReference("Profile/"+request.getUsername()+"/requestedBooks/"+request.getParent());
                    db.child("status").setValue(2);
                    request.setStatus(2);
                    setVisibility(request);
                    setDetails(request);
                }
            });

        }

        public void setVisibility(Request request) {
            if(request.getStatus()!=0)
                itemView.findViewById(R.id.btnApprove).setVisibility(View.GONE);
//                itemView.findViewById(R.id.btnCancel).setVisibility(View.GONE);
            if(request.getStatus()==1)
                itemView.findViewById(R.id.btnConfirmSent).setVisibility(View.VISIBLE);
            if(request.getStatus()==2)
                itemView.findViewById(R.id.btnConfirmSent).setVisibility(View.GONE);
            if(request.getStatus()==3)
                itemView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);
        }
    }
}
