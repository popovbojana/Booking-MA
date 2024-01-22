package com.example.booking_ma.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationsApprovalScreen;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.R;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestDeniedReservationsAdapter  extends RecyclerView.Adapter<GuestDeniedReservationsAdapter.ViewHolder>{

    private List<ReservationDisplayDTO> guestDeniedReservations;
    private Context context;
    private String token;

    public GuestDeniedReservationsAdapter(Context context, List<ReservationDisplayDTO> guestDeniedReservations, String token) {
        this.context = context;
        this.guestDeniedReservations = guestDeniedReservations;
        this.token = token;
    }

    @Override
    public GuestDeniedReservationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new GuestDeniedReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestDeniedReservationsAdapter.ViewHolder holder, int position) {
        ReservationDisplayDTO item = guestDeniedReservations.get(position);

        holder.textViewReservationGuestId.setText("Guest id: "+ item.getId());
        holder.textViewReservationAccommodationId.setText("Accommodation id: "+ item.getAccommodationId());
        holder.textViewReservationGuestsNumber.setText("Guests number: "+ item.getGuestsNumber());
        holder.textViewReservationCheckIn.setText("Check in: "+ item.getCheckIn());
        holder.textViewReservationCheckOut.setText("Check out: "+ item.getCheckOut());
        holder.textViewReservationCost.setText("Cost: "+ item.getTotalCost());
        holder.textViewReservationCancelationsNumber.setText("Cancelations number: "+ item.getGuestCancelationsNumber());

    }

    @Override
    public int getItemCount() {
        return guestDeniedReservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReservationGuestId, textViewReservationAccommodationId, textViewReservationGuestsNumber,
                textViewReservationCheckIn, textViewReservationCheckOut, textViewReservationCost, textViewReservationCancelationsNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewReservationGuestId = itemView.findViewById(R.id.textViewReservationGuestId);
            textViewReservationAccommodationId = itemView.findViewById(R.id.textViewReservationAccommodationId);
            textViewReservationGuestsNumber = itemView.findViewById(R.id.textViewReservationGuestsNumber);
            textViewReservationCheckIn = itemView.findViewById(R.id.textViewReservationCheckIn);
            textViewReservationCheckOut = itemView.findViewById(R.id.textViewReservationCheckOut);
            textViewReservationCost = itemView.findViewById(R.id.textViewReservationCost);
            textViewReservationCancelationsNumber = itemView.findViewById(R.id.textViewReservationCancelationsNumber);

        }
    }
}
