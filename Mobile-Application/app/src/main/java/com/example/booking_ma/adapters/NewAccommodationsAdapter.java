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

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationsApprovalScreen;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAccommodationsAdapter extends RecyclerView.Adapter<NewAccommodationsAdapter.ViewHolder>{

    private List<AccommodationDisplayDTO> accommodations;
    private Context context;
    private String token;

    public NewAccommodationsAdapter(Context context, List<AccommodationDisplayDTO> accommodations, String token) {
        this.context = context;
        this.accommodations = accommodations;
        this.token = token;
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

//        accommodationAvailabilities
        String availabilities = "Available: \n";
        for (AvailabilityDisplayDTO a : item.getAvailabilities()){
            availabilities += a.toString();
        }
        holder.accommodationAvailabilities.setText(availabilities);

        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(true);
                Call<ResponseMessage> call = ServiceUtils.accommodationService(token).approveNewAccommodation(item.getId(), approvalDTO);
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

        holder.btnDisapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(false);
                Call<ResponseMessage> call = ServiceUtils.accommodationService(token).approveNewAccommodation(item.getId(), approvalDTO);
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
        return accommodations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView accommodationImage;
        TextView accommodationName, accommodationDescription, accommodationAmenities, accommodationMinGuests, accommodationMaxGuests, accommodationType, accommodationCancellation, accommodationAddress, accommodationStandardPrice, accommodationAvailabilities;
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
            accommodationAvailabilities = itemView.findViewById(R.id.accommodationAvailabilities);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDisapprove = itemView.findViewById(R.id.btnDisapprove);
        }
    }
}
