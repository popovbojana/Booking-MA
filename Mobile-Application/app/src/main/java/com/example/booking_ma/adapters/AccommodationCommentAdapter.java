package com.example.booking_ma.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationDetailsScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationCommentAdapter extends RecyclerView.Adapter<AccommodationCommentAdapter.ViewHolder>{


    private List<Comment> comments;
    private Context context;

    public AccommodationCommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public AccommodationCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new AccommodationCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccommodationCommentAdapter.ViewHolder holder, int position) {
        Comment item = comments.get(position);

        holder.commentGuestName.setText(item.getUser().getName() + " " + item.getUser().getSurname());
        holder.commentStars.setRating(item.getStars());
        holder.commentMessage.setText(item.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentGuestName;
        RatingBar commentStars;
        TextView commentMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            commentGuestName = itemView.findViewById(R.id.textViewCommentGuestName);
            commentStars = itemView.findViewById(R.id.ratingBarCommentStars);
            commentMessage = itemView.findViewById(R.id.textViewCommentMessage);
        }
    }

}
