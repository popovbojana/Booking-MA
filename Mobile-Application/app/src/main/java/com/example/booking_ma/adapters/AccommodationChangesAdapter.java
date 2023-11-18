package com.example.booking_ma.adapters;

import android.app.Dialog;
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
import com.example.booking_ma.DTO.AccommodationChangeDisplayDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationChangesAdapter extends RecyclerView.Adapter<AccommodationChangesAdapter.ViewHolder>{

    private List<AccommodationChangeDisplayDTO> accommodations;
    private Context context;
    private String token;

    private AccommodationDisplayDTO accommodationDisplay;

    public AccommodationChangesAdapter(Context context, List<AccommodationChangeDisplayDTO> accommodations, String token) {
        this.context = context;
        this.accommodations = accommodations;
        this.token = token;
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
                ApprovalDTO approvalDTO = new ApprovalDTO(true);
                Call<ResponseMessage> call = ServiceUtils.accommodationService(token).approveAccommodationChanges(item.getAccommodationId(), approvalDTO);
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
                intent.putExtra("selectedTab", 1);
                context.startActivity(intent);

            }
        });

        holder.btnDisapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApprovalDTO approvalDTO = new ApprovalDTO(false);
                Call<ResponseMessage> call = ServiceUtils.accommodationService(token).approveAccommodationChanges(item.getAccommodationId(), approvalDTO);
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
                intent.putExtra("selectedTab", 1);
                context.startActivity(intent);
            }
        });

        holder.currentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAccommodation(token, item.getAccommodationId());
            }
        });
    }

    private void showAccommodationInfoDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_accommodation_info);

        TextView accommodationName = dialog.findViewById(R.id.accommodationName);
        String name = "Name: " + accommodationDisplay.getName();
        accommodationName.setText(name);
        TextView accommodationDescription = dialog.findViewById(R.id.accommodationDescription);
        String description = "Description: " + accommodationDisplay.getDescription();
        accommodationDescription.setText(description);
        TextView accommodationAmenities = dialog.findViewById(R.id.accommodationAmenities);
        String amenities = "Amenities: " + accommodationDisplay.getAmenities();
        accommodationAmenities.setText(amenities);
        TextView accommodationMinGuests = dialog.findViewById(R.id.accommodationMinGuests);
        String min = "Min. guests: " + accommodationDisplay.getMinGuests();
        accommodationMinGuests.setText(min);
        TextView accommodationMaxGuests = dialog.findViewById(R.id.accommodationMaxGuests);
        String max = "Max. guests: " + accommodationDisplay.getMaxGuests();
        accommodationMaxGuests.setText(max);
        TextView accommodationType = dialog.findViewById(R.id.accommodationType);
        String type = "Type: " + accommodationDisplay.getType();
        accommodationType.setText(type);
        TextView accommodationCancellation = dialog.findViewById(R.id.accommodationCancellation);
        String cancellation = "Free cancellation up to " + accommodationDisplay.getCancellationDeadlineInDays() + " days";
        accommodationCancellation.setText(cancellation);
        TextView accommodationAddress = dialog.findViewById(R.id.accommodationAddress);
        String address = "Address: " + accommodationDisplay.getAddress();
        accommodationAddress.setText(address);
        TextView accommodationRating = dialog.findViewById(R.id.accommodationRating);
        String rating = "Rating: " + accommodationDisplay.getFinalRating();
        accommodationRating.setText(rating);
        TextView accommodationStandardPrice = dialog.findViewById(R.id.accommodationStandardPrice);
        String price = "Standard price: " + accommodationDisplay.getStandardPrice();
        if (accommodationDisplay.getPriceType() == PriceType.PER_GUEST) {
            price += " per guest";
        } else {
            price += " per unit";
        }
        accommodationStandardPrice.setText(price);

        Button btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadAccommodation(String jwtToken, Long accommodationId) {
        Call<AccommodationDisplayDTO> call = ServiceUtils.accommodationService(jwtToken).getAccommodationById(accommodationId);

        call.enqueue(new Callback<AccommodationDisplayDTO>() {
            @Override
            public void onResponse(Call<AccommodationDisplayDTO> call, Response<AccommodationDisplayDTO> response) {
                if (response.isSuccessful()) {
                    Log.i("Success", response.message());
                    accommodationDisplay = response.body();
                    // Pozivamo dijalog tek kada su podaci dostupni
                    showAccommodationInfoDialog();
                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<AccommodationDisplayDTO> call, Throwable t) {
                Log.d("Fail", t.getMessage());
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
