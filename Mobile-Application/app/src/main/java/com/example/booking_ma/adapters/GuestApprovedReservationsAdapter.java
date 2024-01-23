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
import com.example.booking_ma.GuestReservationsScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestApprovedReservationsAdapter  extends RecyclerView.Adapter<GuestApprovedReservationsAdapter.ViewHolder>{

    private List<ReservationDisplayDTO> guestPendingReservations;
    private Context context;
    private String token;

    public GuestApprovedReservationsAdapter(Context context, List<ReservationDisplayDTO> guestPendingReservations, String token) {
        this.context = context;
        this.guestPendingReservations = guestPendingReservations;
        this.token = token;
    }

    @Override
    public GuestApprovedReservationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_approved_reservation, parent, false);
        return new GuestApprovedReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestApprovedReservationsAdapter.ViewHolder holder, int position) {
        ReservationDisplayDTO item = guestPendingReservations.get(position);

        holder.textViewGuestApprovedReservationGuestId.setText("Guest id: "+ item.getGuestId());
        holder.textViewGuestApprovedReservationAccommodationId.setText("Accommodation id: "+ item.getAccommodationId());
        holder.textViewGuestApprovedReservationGuestsNumber.setText("Guests number: "+ item.getGuestsNumber());
        holder.textViewGuestApprovedReservationCheckIn.setText("Check in: "+ item.getCheckIn());
        holder.textViewGuestApprovedReservationCheckOut.setText("Check out: "+ item.getCheckOut());
        holder.textViewGuestApprovedReservationCost.setText("Cost: "+ item.getTotalCost());
        holder.textViewGuestApprovedReservationCancelationsNumber.setText("Cancelations number: "+ item.getGuestCancelationsNumber());

        holder.btnGuestCancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseMessage> call = ServiceUtils.reservationService(token).cancelReservation(item.getId());
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if(response.isSuccessful()) {

                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, GuestReservationsScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        } else {
                            onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                            holder.textViewGuestApprovedReservationError.setText("Cant cancel, you passed the deadline");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return guestPendingReservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGuestApprovedReservationGuestId, textViewGuestApprovedReservationAccommodationId, textViewGuestApprovedReservationGuestsNumber,
                textViewGuestApprovedReservationCheckIn, textViewGuestApprovedReservationCheckOut, textViewGuestApprovedReservationCost,
                textViewGuestApprovedReservationCancelationsNumber, textViewGuestApprovedReservationError;

        Button btnGuestCancelReservation;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewGuestApprovedReservationGuestId = itemView.findViewById(R.id.textViewGuestApprovedReservationGuestId);
            textViewGuestApprovedReservationAccommodationId = itemView.findViewById(R.id.textViewGuestApprovedReservationAccommodationId);
            textViewGuestApprovedReservationGuestsNumber = itemView.findViewById(R.id.textViewGuestApprovedReservationGuestsNumber);
            textViewGuestApprovedReservationCheckIn = itemView.findViewById(R.id.textViewGuestApprovedReservationCheckIn);
            textViewGuestApprovedReservationCheckOut = itemView.findViewById(R.id.textViewGuestApprovedReservationCheckOut);
            textViewGuestApprovedReservationCost = itemView.findViewById(R.id.textViewGuestApprovedReservationCost);
            textViewGuestApprovedReservationCancelationsNumber = itemView.findViewById(R.id.textViewGuestApprovedReservationCancelationsNumber);
            textViewGuestApprovedReservationError = itemView.findViewById(R.id.textViewGuestApprovedReservationError);

            btnGuestCancelReservation = itemView.findViewById(R.id.btnGuestCancelReservation);

        }
    }
}
