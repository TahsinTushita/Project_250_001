package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder requestHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RequestHolder extends RecyclerView.ViewHolder {

        private TextView username,bookTitle;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }

        public void setDetails(){

        }
    }
}
