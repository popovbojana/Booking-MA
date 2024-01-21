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

public class OwnerDeniedReservationsAdapter  extends RecyclerView.Adapter<OwnerDeniedReservationsAdapter.ViewHolder>{

    private List<ReservationDisplayDTO> ownerDeniedReservations;
    private Context context;
    private String token;

    public OwnerDeniedReservationsAdapter(Context context, List<ReservationDisplayDTO> ownerDeniedReservations, String token) {
        this.context = context;
        this.ownerDeniedReservations = ownerDeniedReservations;
        this.token = token;
    }

    @Override
    public OwnerDeniedReservationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new OwnerDeniedReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnerDeniedReservationsAdapter.ViewHolder holder, int position) {
        ReservationDisplayDTO item = ownerDeniedReservations.get(position);

        holder.textViewReservationGuestId.setText("Guest id: "+ item.getId());
        holder.textViewReservationAccommodationId.setText("Accommodation id: "+ item.getId());
        holder.textViewReservationGuestsNumber.setText("Guests number: "+ item.getId());
        holder.textViewReservationCheckIn.setText("Check in: "+ item.getId());
        holder.textViewReservationCheckOut.setText("Check out: "+ item.getId());
        holder.textViewReservationCost.setText("Cost: "+ item.getId());
        holder.textViewReservationCancelationsNumber.setText("Cancelations number: "+ item.getId());

    }

    @Override
    public int getItemCount() {
        return ownerDeniedReservations.size();
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
