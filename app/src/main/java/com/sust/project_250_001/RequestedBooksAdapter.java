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

public class RequestedBooksAdapter extends RecyclerView.Adapter<RequestedBooksAdapter.RequestedBooksHolder> {

    public interface OnItemClickListener {
        void onItemClick(Request request);
    }

    private Context context;
    private ArrayList<Request> requestArrayList;

    public RequestedBooksAdapter(Context context, ArrayList<Request> requestArrayList) {
        this.context = context;
        this.requestArrayList = requestArrayList;
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
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class RequestedBooksHolder extends RecyclerView.ViewHolder {

        private TextView requestedUser,requestedBook,status;

        public RequestedBooksHolder(@NonNull View itemView) {
            super(itemView);
            requestedUser = itemView.findViewById(R.id.requestedUser);
            requestedBook = itemView.findViewById(R.id.requestedBook);
            status = itemView.findViewById(R.id.statusid);
        }

        public void setDetails(Request request){
            requestedUser.setText(request.getUsername());
            requestedBook.setText(request.getBookTitle());
            //status.setText(request.getStatus());
        }

        public void bind(final Request request,final RequestedBooksAdapter.OnItemClickListener listener){

        }
    }
}
