package com.example.booking_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.R;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.model.RatingComment;

import java.util.List;

public class AccommodationRatingCommentAdapter extends RecyclerView.Adapter<AccommodationRatingCommentAdapter.ViewHolder>{


    private List<RatingComment> comments;
    private Context context;

    public AccommodationRatingCommentAdapter(Context context, List<RatingComment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public AccommodationRatingCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new AccommodationRatingCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccommodationRatingCommentAdapter.ViewHolder holder, int position) {
        RatingComment item = comments.get(position);

        holder.commentGuestName.setText(item.getGuest().getName()+ " " + item.getGuest().getSurname());
        holder.commentStars.setRating(item.getRating());
        holder.commentMessage.setText(item.getComment());

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
