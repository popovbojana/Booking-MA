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
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerPendingReservationsAdapter  extends RecyclerView.Adapter<OwnerPendingReservationsAdapter.ViewHolder>{

    private List<ReservationDisplayDTO> ownerPendingReservations;
    private Context context;
    private String token;

    public OwnerPendingReservationsAdapter(Context context, List<ReservationDisplayDTO> ownerPendingReservations, String token) {
        this.context = context;
        this.ownerPendingReservations = ownerPendingReservations;
        this.token = token;
    }

    @Override
    public OwnerPendingReservationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_pending_reservation, parent, false);
        return new OwnerPendingReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnerPendingReservationsAdapter.ViewHolder holder, int position) {
        ReservationDisplayDTO item = ownerPendingReservations.get(position);

        holder.textViewOwnerPendingReservationGuestId.setText("Guest id: "+ item.getId());
        holder.textViewOwnerPendingReservationAccommodationId.setText("Accommodation id: "+ item.getId());
        holder.textViewOwnerPendingReservationGuestsNumber.setText("Guests number: "+ item.getId());
        holder.textViewOwnerPendingReservationCheckIn.setText("Check in: "+ item.getId());
        holder.textViewOwnerPendingReservationCheckOut.setText("Check out: "+ item.getId());
        holder.textViewOwnerPendingReservationCost.setText("Cost: "+ item.getId());
        holder.textViewOwnerPendingReservationCancelationsNumber.setText("Cancelations number: "+ item.getId());

        holder.btnOwnerDenyReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseMessage> call = ServiceUtils.reservationService(token).denyReservation(item.getId());
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

        holder.btnOwnerApproveReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseMessage> call = ServiceUtils.reservationService(token).approveReservation(item.getId());
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
        return ownerPendingReservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOwnerPendingReservationGuestId, textViewOwnerPendingReservationAccommodationId, textViewOwnerPendingReservationGuestsNumber,
                textViewOwnerPendingReservationCheckIn, textViewOwnerPendingReservationCheckOut, textViewOwnerPendingReservationCost, textViewOwnerPendingReservationCancelationsNumber;

        Button btnOwnerDenyReservation, btnOwnerApproveReservation;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewOwnerPendingReservationGuestId = itemView.findViewById(R.id.textViewOwnerPendingReservationGuestId);
            textViewOwnerPendingReservationAccommodationId = itemView.findViewById(R.id.textViewOwnerPendingReservationAccommodationId);
            textViewOwnerPendingReservationGuestsNumber = itemView.findViewById(R.id.textViewOwnerPendingReservationGuestsNumber);
            textViewOwnerPendingReservationCheckIn = itemView.findViewById(R.id.textViewOwnerPendingReservationCheckIn);
            textViewOwnerPendingReservationCheckOut = itemView.findViewById(R.id.textViewOwnerPendingReservationCheckOut);
            textViewOwnerPendingReservationCost = itemView.findViewById(R.id.textViewOwnerPendingReservationCost);
            textViewOwnerPendingReservationCancelationsNumber = itemView.findViewById(R.id.textViewOwnerPendingReservationCancelationsNumber);
            btnOwnerDenyReservation = itemView.findViewById(R.id.btnOwnerDenyReservation);
            btnOwnerApproveReservation = itemView.findViewById(R.id.btnOwnerApproveReservation);
        }
    }
}
