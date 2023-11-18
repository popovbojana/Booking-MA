package com.example.booking_ma.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.AccommodationChangeDisplayDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.EditAccommodationScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;

import java.util.List;

public class AccommodationChangesAdapter extends RecyclerView.Adapter<AccommodationChangesAdapter.ViewHolder>{

    private List<AccommodationChangeDisplayDTO> accommodations;
    private Context context;

    public AccommodationChangesAdapter(Context context, List<AccommodationChangeDisplayDTO> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @Override
    public AccommodationChangesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation_change, parent, false);
        return new AccommodationChangesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccommodationChangesAdapter.ViewHolder holder, int position) {
        AccommodationChangeDisplayDTO item = accommodations.get(position);

        String name = "New name: " + item.getNewName();
        holder.accommodationName.setText(name);
        String description = "New description: " + item.getNewDescription();
        holder.accommodationDescription.setText(description);
        String amenities = "New amenities: " + item.getNewAmenities();
        holder.accommodationAmenities.setText(amenities);
        String minGuests = "New min. guests: ";
        if (item.getNewMinGuests() != -1){
            minGuests += item.getNewMinGuests();
        }
        holder.accommodationMinGuests.setText(minGuests);
        String maxGuests = "New max. guests: ";
        if (item.getNewMaxGuests() != -1){
            maxGuests += item.getNewMaxGuests();
        }
        holder.accommodationMaxGuests.setText(maxGuests);
        String type = "New accommodation type: " + item.getNewType();
        holder.accommodationType.setText(type);
        String cancellation = "New cancellation deadline: ";
        if (item.getNewCancellationDeadlineInDays() != -1) {
            cancellation += item.getNewCancellationDeadlineInDays();
        }
        cancellation += " days";
        holder.accommodationCancellation.setText(cancellation);
        String standardPrice = "New standard price: ";
        if (item.getNewStandardPrice() != -1.0){
            standardPrice += item.getNewStandardPrice();
        }
        holder.accommodationStandardPrice.setText(standardPrice);
        String priceType = "New price type: ";
        if (item.getNewPriceType() == PriceType.PER_GUEST){
            priceType += "per guest";
        } else if (item.getNewPriceType() == PriceType.PER_UNIT){
            priceType += "per unit";
        }

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        holder.btnDisapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        holder.currentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView accommodationImage;
        TextView accommodationName, accommodationDescription, accommodationAmenities, accommodationMinGuests, accommodationMaxGuests, accommodationType, accommodationCancellation, accommodationStandardPrice, accommodationPriceType;
        Button btnApprove, btnDisapprove, currentInfoButton;

        public ViewHolder(View itemView) {
            super(itemView);
            accommodationImage = itemView.findViewById(R.id.accommodationImage);
            accommodationName = itemView.findViewById(R.id.accommodationName);
            accommodationDescription = itemView.findViewById(R.id.accommodationDescription);
            accommodationAmenities = itemView.findViewById(R.id.accommodationAmenities);
            accommodationMinGuests = itemView.findViewById(R.id.accommodationMinGuests);
            accommodationMaxGuests = itemView.findViewById(R.id.accommodationMaxGuests);
            accommodationType = itemView.findViewById(R.id.accommodationType);
            accommodationCancellation = itemView.findViewById(R.id.accommodationCancellation);
            accommodationStandardPrice = itemView.findViewById(R.id.accommodationStandardPrice);
            accommodationPriceType = itemView.findViewById(R.id.accommodationPriceType);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDisapprove = itemView.findViewById(R.id.btnDisapprove);
            currentInfoButton = itemView.findViewById(R.id.currentInfoButton);
        }
    }
}
