package com.example.booking_ma.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ReportedUserReasonDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.R;
import com.example.booking_ma.service.ServiceUtils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            String guest = "Guest: " + comment.getGuest();
            holder.textViewGuest.setText(guest);
            String time = "Time: " + comment.getTime();
            holder.textViewTime.setText(time);
            String rating = "Rating: " + comment.getRating();
            holder.textViewRating.setText(rating);
            String commentText = "Comment: " + comment.getComment();
            holder.textViewComment.setText(commentText);

            holder.buttonReportComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseMessage> call = ServiceUtils.ratingCommentService(token).report(comment.getId());
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if(response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                        }
                    });

                }
            });

            holder.buttonReportGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogReportGuest(comment.getGuestsId());
                }
            });
        }
    }

    public void showDialogReportGuest(Long guestId){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_reason);

        EditText editTextReason = dialog.findViewById(R.id.editTextReason);
        ReportedUserReasonDTO reason = new ReportedUserReasonDTO(editTextReason.getText().toString());

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseMessage> call = ServiceUtils.userService(token).reportGuest(guestId, reason);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if(response.isSuccessful()) {
                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                onFailure(call, new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGuest;
        TextView textViewTime;
        TextView textViewRating;
        TextView textViewComment;
        Button buttonReportComment, buttonReportGuest;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGuest = itemView.findViewById(R.id.textViewGuest);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            buttonReportComment = itemView.findViewById(R.id.buttonReportComment);
            buttonReportGuest = itemView.findViewById(R.id.buttonReportGuest);
        }
    }
}
