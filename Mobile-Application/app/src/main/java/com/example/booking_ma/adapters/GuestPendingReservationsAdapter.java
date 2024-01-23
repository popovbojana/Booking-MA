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

public class GuestPendingReservationsAdapter  extends RecyclerView.Adapter<GuestPendingReservationsAdapter.ViewHolder>{

    private List<ReservationDisplayDTO> guestPendingReservations;
    private Context context;
    private String token;

    public GuestPendingReservationsAdapter(Context context, List<ReservationDisplayDTO> guestPendingReservations, String token) {
        this.context = context;
        this.guestPendingReservations = guestPendingReservations;
        this.token = token;
    }

    @Override
    public GuestPendingReservationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_pending_reservation, parent, false);
        return new GuestPendingReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestPendingReservationsAdapter.ViewHolder holder, int position) {
        ReservationDisplayDTO item = guestPendingReservations.get(position);

        holder.textViewGuestPendingReservationGuestId.setText("Guest id: "+ item.getGuestId());
        holder.textViewGuestPendingReservationAccommodationId.setText("Accommodation id: "+ item.getAccommodationId());
        holder.textViewGuestPendingReservationGuestsNumber.setText("Guests number: "+ item.getGuestsNumber());
        holder.textViewGuestPendingReservationCheckIn.setText("Check in: "+ item.getCheckIn());
        holder.textViewGuestPendingReservationCheckOut.setText("Check out: "+ item.getCheckOut());
        holder.textViewGuestPendingReservationCost.setText("Cost: "+ item.getTotalCost());
        holder.textViewGuestPendingReservationCancelationsNumber.setText("Cancelations number: "+ item.getGuestCancelationsNumber());

        holder.btnDeleteReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseMessage> call = ServiceUtils.reservationService(token).deleteReservation(item.getId());
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
                Intent intent = new Intent(context, GuestReservationsScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return guestPendingReservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGuestPendingReservationGuestId, textViewGuestPendingReservationAccommodationId, textViewGuestPendingReservationGuestsNumber,
                textViewGuestPendingReservationCheckIn, textViewGuestPendingReservationCheckOut, textViewGuestPendingReservationCost, textViewGuestPendingReservationCancelationsNumber;

        Button btnDeleteReservation;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewGuestPendingReservationGuestId = itemView.findViewById(R.id.textViewGuestPendingReservationGuestId);
            textViewGuestPendingReservationAccommodationId = itemView.findViewById(R.id.textViewGuestPendingReservationAccommodationId);
            textViewGuestPendingReservationGuestsNumber = itemView.findViewById(R.id.textViewGuestPendingReservationGuestsNumber);
            textViewGuestPendingReservationCheckIn = itemView.findViewById(R.id.textViewGuestPendingReservationCheckIn);
            textViewGuestPendingReservationCheckOut = itemView.findViewById(R.id.textViewGuestPendingReservationCheckOut);
            textViewGuestPendingReservationCost = itemView.findViewById(R.id.textViewGuestPendingReservationCost);
            textViewGuestPendingReservationCancelationsNumber = itemView.findViewById(R.id.textViewGuestPendingReservationCancelationsNumber);
            btnDeleteReservation = itemView.findViewById(R.id.btnDeleteReservation);

        }
    }
}
