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

import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.EditAccommodationScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;

import java.util.List;

public class NewAccommodationsAdapter extends RecyclerView.Adapter<NewAccommodationsAdapter.ViewHolder>{

    private List<AccommodationDisplayDTO> accommodations;
    private Context context;

    public NewAccommodationsAdapter(Context context, List<AccommodationDisplayDTO> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @Override
    public NewAccommodationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_accommodation, parent, false);
        return new NewAccommodationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewAccommodationsAdapter.ViewHolder holder, int position) {
        AccommodationDisplayDTO item = accommodations.get(position);

//        holder.accommodationImage.setImageResource(item.getImageResource());
        holder.accommodationName.setText(item.getName());
        holder.accommodationDescription.setText(item.getDescription());
        holder.accommodationAmenities.setText(item.getAmenities());
        String minGuests = "Min. guests: " + item.getMinGuests();
        holder.accommodationMinGuests.setText(minGuests);
        String maxGuests = "Max. guests: " + item.getMaxGuests();
        holder.accommodationMaxGuests.setText(maxGuests);
        String type = "Accommodation type: " + item.getType();
        holder.accommodationType.setText(type);
        String cancellation = "Free cancellation up to " + item.getCancellationDeadlineInDays() + " days before check-in";
        holder.accommodationCancellation.setText(cancellation);
        holder.accommodationAddress.setText(item.getAddress());
        String price;
        if (item.getPriceType() == PriceType.PER_GUEST){
            price = item.getStandardPrice() + " per guest";
        } else {
            price = item.getStandardPrice() + " per unit";
        }
        holder.accommodationStandardPrice.setText(price);

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
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView accommodationImage;
        TextView accommodationName, accommodationDescription, accommodationAmenities, accommodationMinGuests, accommodationMaxGuests, accommodationType, accommodationCancellation, accommodationAddress, accommodationStandardPrice;
        Button btnApprove, btnDisapprove;

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
            accommodationAddress = itemView.findViewById(R.id.accommodationAddress);
            accommodationStandardPrice = itemView.findViewById(R.id.accommodationStandardPrice);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDisapprove = itemView.findViewById(R.id.btnDisapprove);
        }
    }
}
