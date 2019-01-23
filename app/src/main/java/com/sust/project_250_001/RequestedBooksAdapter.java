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

public class RequestedBooksAdapter extends RecyclerView.Adapter<RequestedBooksAdapter.RequestedBooksHolder> {

    public interface OnItemClickListener {
        void onItemClick(Request request);
    }

    private Context context;
    private ArrayList<Request> requestArrayList;
    private FirebaseDatabase database;
    private final RequestedBooksAdapter.OnItemClickListener listener;


    public RequestedBooksAdapter(Context context, ArrayList<Request> requestArrayList,OnItemClickListener listener,FirebaseDatabase database) {
        this.context = context;
        this.requestArrayList = requestArrayList;
        this.database = database;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestedBooksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.requestedbooks_recyclerview,viewGroup,false);
        return new RequestedBooksAdapter.RequestedBooksHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedBooksHolder requestedBooksHolder, int i) {
        requestedBooksHolder.setDetails(requestArrayList.get(i));
        requestedBooksHolder.bind(requestArrayList.get(i),listener);
        requestedBooksHolder.setVisibility(requestArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class RequestedBooksHolder extends RecyclerView.ViewHolder {

        private TextView requestedUser, requestedBook, status;

        public RequestedBooksHolder(@NonNull View itemView) {
            super(itemView);
            requestedUser = itemView.findViewById(R.id.requestedUser);
            requestedBook = itemView.findViewById(R.id.requestedBook);
            status = itemView.findViewById(R.id.statusid);
        }

        public void setDetails(Request request) {
            requestedUser.setText(request.getUsername());
            requestedBook.setText(request.getBookTitle());

            if (request.getStatus() == 0) {
                status.setText("pending");
            } else if (request.getStatus() == 1) {
                status.setText("approved");
            } else if (request.getStatus() == 2) {
                status.setText("confirmed sent");
            } else if (request.getStatus() == 3) {
                status.setText("confirm recieved");
            }
        }


        public void bind(final Request request, OnItemClickListener listener) {
            itemView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestArrayList.remove(request);
                    notifyItemRemoved(getAdapterPosition());
                    DatabaseReference db = database.getReference("Profile/" + request.getUsername()
                            + "/booklist/" + request.getParent()
                            + "/requests/" + LoginActivity.user);
                    db.setValue(null); //0 for pending,1 for approved,2 for confirmed

                    db = database.getReference("Profile/" + LoginActivity.user
                            + "/requestedBooks/" + request.getParent());
                    db.setValue(null); //0 for pending,1 for approved,2 for confirmed


                }
            });

            itemView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = database.getReference("Profile/" + request.getUsername() +
                            "/booklist/" + request.getParent() + "/requests/" + LoginActivity.user);
                    databaseReference.child("status").setValue(3);

                    databaseReference = database.getReference("Profile/" + LoginActivity.user + "/requestedBooks/" +
                            request.getParent());
                    databaseReference.child("status").setValue(3);
                    request.setStatus(3);
                    setVisibility(request);
                    setDetails(request);

                    databaseReference = database.getReference("Profile/"+LoginActivity.user+"/borrowedBooks/"+
                                                                    request.getParent());
                    databaseReference.child("username").setValue(request.getUsername());

                    databaseReference = database.getReference("Profile/"+request.getUsername()+"/lentBooks/"+
                                                                request.getParent());
                    databaseReference.child("username").setValue(LoginActivity.user);
                }
            });

        }
        public void setVisibility(Request request){
            if(request.getStatus()==2){
                itemView.findViewById(R.id.confirmBtn).setVisibility(View.VISIBLE);
            }
            if(request.getStatus()==3){
                itemView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);
                itemView.findViewById(R.id.confirmBtn).setVisibility(View.GONE);
            }
        }
    }


    }
