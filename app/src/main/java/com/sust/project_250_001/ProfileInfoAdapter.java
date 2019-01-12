package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ProfileInfoHolder> {
    @NonNull
    @Override
    public ProfileInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileInfoHolder profileInfoHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfileInfoHolder extends RecyclerView.ViewHolder {

        TextView username,email,booklist,wishlist,address;

        public ProfileInfoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
