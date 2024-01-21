package com.example.booking_ma.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationDetailsScreen;
import com.example.booking_ma.AccommodationsApprovalScreen;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.FavoriteAccommodationDTO;
import com.example.booking_ma.DTO.ReportedUserReasonDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.FavoriteAccommodationsScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAccommodationsAdapter extends RecyclerView.Adapter<FavoriteAccommodationsAdapter.CommentViewHolder> {

    private List<AccommodationDisplayDTO> accommodations;
    private Context context;
    private String token;
    private Long myId;

    public FavoriteAccommodationsAdapter(Context context, List<AccommodationDisplayDTO> accommodations, String token, Long myId) {
        this.context = context;
        this.accommodations = accommodations;
        this.token = token;
        this.myId = myId;
    }

    public void setComments(List<AccommodationDisplayDTO> accommodations) {
        this.accommodations = accommodations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAccommodationsAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_accommodation, parent, false);
        return new FavoriteAccommodationsAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAccommodationsAdapter.CommentViewHolder holder, int position) {
        if (accommodations != null) {
            AccommodationDisplayDTO accommodationDisplay = accommodations.get(position);

            String name = "Name: " + accommodationDisplay.getName();
            holder.accommodationName.setText(name);
            String description = "Description: " + accommodationDisplay.getDescription();
            holder.accommodationDescription.setText(description);
            String amenities = "Amenities: " + accommodationDisplay.getAmenities();
            holder.accommodationAmenities.setText(amenities);
            String min = "Min. guests: " + accommodationDisplay.getMinGuests();
            holder.accommodationMinGuests.setText(min);
            String max = "Max. guests: " + accommodationDisplay.getMaxGuests();
            holder.accommodationMaxGuests.setText(max);
            String type = "Type: " + accommodationDisplay.getType();
            holder.accommodationType.setText(type);
            String cancellation = "Free cancellation up to " + accommodationDisplay.getCancellationDeadlineInDays() + " days";
            holder.accommodationCancellation.setText(cancellation);
            String address = "Address: " + accommodationDisplay.getAddress();
            holder.accommodationAddress.setText(address);
            String price = "Standard price: " + accommodationDisplay.getStandardPrice();
            if (accommodationDisplay.getPriceType() == PriceType.PER_GUEST) {
                price += " per guest";
            } else {
                price += " per unit";
            }
            holder.accommodationStandardPrice.setText(price);
            String availabilities = "Available: \n";
            for (AvailabilityDisplayDTO a : accommodationDisplay.getAvailabilities()){
                availabilities += a.toString();
            }
            holder.accommodationAvailabilities.setText(availabilities);

            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteAccommodationDTO favoriteAccommodationDTO = new FavoriteAccommodationDTO(myId, accommodationDisplay.getId());
                    Call<ResponseMessage> call = ServiceUtils.accommodationService(token).removeFromFavorites(favoriteAccommodationDTO);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if(response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    onFailure(call, new Throwable(response.errorBody().string()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(context, FavoriteAccommodationsScreen.class);
                    context.startActivity(intent);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AccommodationDetailsScreen.class);

                    ArrayList<Comment> commenstArrayList = new ArrayList<>();
                    ArrayList<LocalDate> reservedDatesArrayList = new ArrayList<>();
                    ArrayList<LocalDate> freeDatesArrayList= new ArrayList<>();
                    ArrayList<String> amentitesArrayList= new ArrayList<>();

                    intent.putExtra("a_name", accommodationDisplay.getName());
                    intent.putExtra("a_img", R.drawable.item);
                    intent.putExtra("a_description", accommodationDisplay.getDescription());
                    intent.putExtra("a_price", accommodationDisplay.getStandardPrice());
                    intent.putExtra("a_stars", accommodationDisplay.getFinalRating());
                    intent.putExtra("a_location_name", accommodationDisplay.getAddress());
                    intent.putExtra("a_location_lat", accommodationDisplay.getLatitude());
                    intent.putExtra("a_location_long", accommodationDisplay.getLongitude());
                    intent.putExtra("a_amentites", accommodationDisplay.getAmenities());
                    intent.putExtra("a_owner_id", accommodationDisplay.getOwnersId());
                    intent.putExtra("a_accommodation_id", accommodationDisplay.getId());

                    context.startActivity(intent);
                }
            });

        }
    }

    public void showDialogReportGuest(Long guestId){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_reason);

        EditText editTextReason = dialog.findViewById(R.id.editTextReason);
        ReportedUserReasonDTO reason = new ReportedUserReasonDTO(editTextReason.getText().toString());

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseMessage> call = ServiceUtils.userService(token).reportGuest(guestId, reason);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if(response.isSuccessful()) {
                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                onFailure(call, new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                dialog.dismiss();
            }
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return accommodations != null ? accommodations.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView accommodationName, accommodationDescription, accommodationAmenities, accommodationMinGuests, accommodationMaxGuests, accommodationType, accommodationCancellation, accommodationAddress, accommodationStandardPrice, accommodationAvailabilities;
        Button btnRemove;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
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

            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
