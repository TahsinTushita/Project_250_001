package com.sust.project_250_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {

    private Context context;
    private ArrayList<Request> requestArrayList;

    public RequestAdapter(Context context,ArrayList<Request> requestArrayList) {
        this.context = context;
        this.requestArrayList = requestArrayList;
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
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder {

        private TextView username,bookTitle;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }

        public void setDetails(Request request){
            bookTitle.setText(request.getBookTitle());
            username.setText(request.getUsername());
        }
    }
}
