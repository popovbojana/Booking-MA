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
import com.example.booking_ma.ReportedUsersScreen;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedGuestsAdapter  extends RecyclerView.Adapter<ReportedGuestsAdapter.ViewHolder>{

    private List<UserDisplayDTO> reportedGuests;
    private Context context;
    private String token;

    public ReportedGuestsAdapter(Context context, List<UserDisplayDTO> reportedGuests, String token) {
        this.context = context;
        this.reportedGuests = reportedGuests;
        this.token = token;
    }

    @Override
    public ReportedGuestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_report, parent, false);
        return new ReportedGuestsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportedGuestsAdapter.ViewHolder holder, int position) {
        UserDisplayDTO item = reportedGuests.get(position);

        holder.textViewGuestReportedId.setText("Guest id: "+ item.getId());
        holder.textViewGuestReportedName.setText("Name: " + item.getName());
        holder.textViewGuestReportedSurname.setText("Surname: " + item.getSurname());
        holder.textViewGuestReportedReason.setText("Reason: " + item.getReportedReason());

        holder.btnGuestReportedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(false);
                Call<ResponseMessage> call = ServiceUtils.userService(token).handleReportedGuest(item.getId(), approvalDTO);
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
                Intent intent = new Intent(context, ReportedUsersScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnGuestReportedBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(true);
                Call<ResponseMessage> call = ServiceUtils.userService(token).handleReportedGuest(item.getId(), approvalDTO);
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
                Intent intent = new Intent(context, ReportedUsersScreen .class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportedGuests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGuestReportedId, textViewGuestReportedName, textViewGuestReportedSurname, textViewGuestReportedReason;
        Button btnGuestReportedBlock, btnGuestReportedCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewGuestReportedId = itemView.findViewById(R.id.textViewGuestReportedId);
            textViewGuestReportedName = itemView.findViewById(R.id.textViewGuestReportedName);
            textViewGuestReportedSurname = itemView.findViewById(R.id.textViewGuestReportedSurname);
            textViewGuestReportedReason = itemView.findViewById(R.id.textViewGuestReportedReason);
            btnGuestReportedBlock = itemView.findViewById(R.id.btnGuestReportedBlock);
            btnGuestReportedCancel = itemView.findViewById(R.id.btnGuestReportedCancel);
        }
    }
}
