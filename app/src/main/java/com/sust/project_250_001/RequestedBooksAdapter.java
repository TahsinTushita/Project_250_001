package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RequestedBooksAdapter extends RecyclerView.Adapter<RequestedBooksAdapter.RequestedBooksHolder> {
    @NonNull
    @Override
    public RequestedBooksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedBooksHolder requestedBooksHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RequestedBooksHolder extends RecyclerView.ViewHolder {
        public RequestedBooksHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
