package com.example.booking_ma.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationsApprovalScreen;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedOwnersCommentsAdapter  extends RecyclerView.Adapter<ReportedOwnersCommentsAdapter.ViewHolder>{

    private List<RatingCommentDisplayDTO> reportedOwnersComments;
    private Context context;
    private String token;

    public ReportedOwnersCommentsAdapter(Context context, List<RatingCommentDisplayDTO> reportedOwnersComments, String token) {
        this.context = context;
        this.reportedOwnersComments = reportedOwnersComments;
        this.token = token;
    }

    @Override
    public ReportedOwnersCommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reported_comment, parent, false);
        return new ReportedOwnersCommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportedOwnersCommentsAdapter.ViewHolder holder, int position) {
        RatingCommentDisplayDTO item = reportedOwnersComments.get(position);

        holder.textViewReportCommentGuest.setText("Guest id: "+ item.getGuestsId());
        holder.textViewReportCommentOwner.setText("Owner ID: " + item.getOwnersId());
        holder.textViewReportCommentTime.setText("Time: " + item.getTime());
        holder.textViewReportCommentRating.setText("Rating: " + item.getRating());
        holder.textViewReportCommentComment.setText("Comment: " + item.getComment());

        holder.btnReportCommentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(false);
                Call<ResponseMessage> call = ServiceUtils.ratingCommentService(token).handleReportedComment(item.getId(), approvalDTO);
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
                Intent intent = new Intent(context, AccommodationsApprovalScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnReportCommentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(true);
                Call<ResponseMessage> call = ServiceUtils.ratingCommentService(token).handleReportedComment(item.getId(), approvalDTO);
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
                Intent intent = new Intent(context, AccommodationsApprovalScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportedOwnersComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReportCommentGuest, textViewReportCommentOwner, textViewReportCommentTime, textViewReportCommentRating, textViewReportCommentComment;
        Button btnReportCommentCancel, btnReportCommentDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewReportCommentGuest = itemView.findViewById(R.id.textViewReportCommentGuest);
            textViewReportCommentOwner = itemView.findViewById(R.id.textViewReportCommentOwner);
            textViewReportCommentTime = itemView.findViewById(R.id.textViewReportCommentTime);
            textViewReportCommentRating = itemView.findViewById(R.id.textViewReportCommentRating);
            textViewReportCommentComment = itemView.findViewById(R.id.textViewReportCommentComment);
            btnReportCommentCancel = itemView.findViewById(R.id.btnReportCommentCancel);
            btnReportCommentDelete = itemView.findViewById(R.id.btnReportCommentDelete);
        }
    }
}
