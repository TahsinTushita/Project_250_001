package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {



    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        public ListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
