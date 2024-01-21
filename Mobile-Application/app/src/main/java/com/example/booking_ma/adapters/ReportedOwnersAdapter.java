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

public class ReportedOwnersAdapter  extends RecyclerView.Adapter<ReportedOwnersAdapter.ViewHolder>{

    private List<UserDisplayDTO> reportedOwners;
    private Context context;
    private String token;

    public ReportedOwnersAdapter(Context context, List<UserDisplayDTO> reportedOwners, String token) {
        this.context = context;
        this.reportedOwners = reportedOwners;
        this.token = token;
    }

    @Override
    public ReportedOwnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_report, parent, false);
        return new ReportedOwnersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportedOwnersAdapter.ViewHolder holder, int position) {
        UserDisplayDTO item = reportedOwners.get(position);

        holder.textViewOwnerReportedId.setText("Guest id: "+ item.getId());
        holder.textViewOwnerReportedName.setText("Name: " + item.getName());
        holder.textViewOwnerReportedSurname.setText("Surname: " + item.getSurname());
        holder.textViewOwnerReportedReason.setText("Reason: " + item.getReportedReason());
    }

    @Override
    public int getItemCount() {
        return reportedOwners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOwnerReportedId, textViewOwnerReportedName, textViewOwnerReportedSurname, textViewOwnerReportedReason;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewOwnerReportedId = itemView.findViewById(R.id.textViewOwnerReportedId);
            textViewOwnerReportedName = itemView.findViewById(R.id.textViewOwnerReportedName);
            textViewOwnerReportedSurname = itemView.findViewById(R.id.textViewOwnerReportedSurname);
            textViewOwnerReportedReason = itemView.findViewById(R.id.textViewOwnerReportedReason);
        }
    }
}
