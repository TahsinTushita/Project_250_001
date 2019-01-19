package com.sust.project_250_001;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        private TextView username,email,address;
        private Button requestBtn;

        public ProfileInfoHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.profileUsername);
            email = itemView.findViewById(R.id.profileEmail);
            address = itemView.findViewById(R.id.profileAddress);
        }

        public void setDetails(ProfileInfo profileInfo){
            username.setText(profileInfo.getUsername());
            email.setText(profileInfo.getEmail());
            address.setText(profileInfo.getAddress());
        }
    }
}
