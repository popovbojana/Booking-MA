package com.example.booking_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.R;

import java.util.List;

public class RatingCommentAdapter extends RecyclerView.Adapter<RatingCommentAdapter.CommentViewHolder> {

    private List<RatingCommentDisplayDTO> comments;
    private Context context;
    private String token;

    public RatingCommentAdapter(Context context, List<RatingCommentDisplayDTO> comments, String token) {
        this.context = context;
        this.comments = comments;
        this.token = token;
    }

    public void setComments(List<RatingCommentDisplayDTO> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (comments != null) {
            RatingCommentDisplayDTO comment = comments.get(position);

            String guest = "Guest ID: " + comment.getGuestsId();
            holder.textViewGuestId.setText(guest);
            String time = "Time: " + comment.getTime();
            holder.textViewTime.setText(time);
            String rating = "Rating: " + comment.getRating();
            holder.textViewRating.setText(rating);
            String commentText = "Comment: " + comment.getComment();
            holder.textViewComment.setText(commentText);

            holder.buttonReport.setOnClickListener(v -> {
                //todo
            });
        }
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGuestId;
        TextView textViewTime;
        TextView textViewRating;
        TextView textViewComment;
        Button buttonReport;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGuestId = itemView.findViewById(R.id.textViewGuestId);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            buttonReport = itemView.findViewById(R.id.buttonReport);
        }
    }
}
