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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.EditAccommodationScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostAccommodationsAdapter extends RecyclerView.Adapter<HostAccommodationsAdapter.ViewHolder> {

    private List<AccommodationDisplayDTO> accommodations;
    private Context context;
    private String token;
    private List<RatingCommentDisplayDTO> comments;

    public HostAccommodationsAdapter(Context context, List<AccommodationDisplayDTO> accommodations, String token) {
        this.context = context;
        this.accommodations = accommodations;
        this.token = token;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_accommodation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccommodationDisplayDTO item = accommodations.get(position);

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
        String rating = "Rating: " + item.getFinalRating();
        holder.accommodationRating.setText(rating);
        String price;
        if (item.getPriceType() == PriceType.PER_GUEST){
            price = item.getStandardPrice() + " per guest";
        } else {
            price = item.getStandardPrice() + " per unit";
        }
        holder.accommodationStandardPrice.setText(price);
        String availabilities = "Available: \n";
        for (AvailabilityDisplayDTO a : item.getAvailabilities()){
            availabilities += a.toString();
        }
        holder.accommodationAvailabilities.setText(availabilities);

        holder.commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentsDialog(item.getId());
            }
        });

        holder.getReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Handle reporting
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAccommodationScreen.class);
                intent.putExtra("accommodationId", item.getId());
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
        TextView accommodationName, accommodationDescription, accommodationAmenities, accommodationMinGuests, accommodationMaxGuests, accommodationType, accommodationCancellation, accommodationAddress, accommodationRating, accommodationStandardPrice, accommodationAvailabilities;
        Button commentsButton, getReportButton, editButton;

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
            accommodationRating = itemView.findViewById(R.id.accommodationRating);
            accommodationStandardPrice = itemView.findViewById(R.id.accommodationStandardPrice);
            accommodationAvailabilities = itemView.findViewById(R.id.accommodationAvailabilities);
            commentsButton = itemView.findViewById(R.id.commentsButton);
            getReportButton = itemView.findViewById(R.id.getReportButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

    private void showCommentsDialog(Long accommodationId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_comments);

        RecyclerView recyclerViewComments = dialog.findViewById(R.id.recyclerViewComments);
        RatingCommentAdapter adapter = new RatingCommentAdapter(context, comments, token);
        recyclerViewComments.setAdapter(adapter);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(context));

        Button closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        getAllComments(token, accommodationId, adapter);

        dialog.show();
    }

    private void getAllComments(String jwtToken, Long id, RatingCommentAdapter adapter) {
        Call<AllRatingsDisplay> call = ServiceUtils.ratingCommentService(jwtToken).getAllForAccommodation(id);

        call.enqueue(new Callback<AllRatingsDisplay>() {
            @Override
            public void onResponse(Call<AllRatingsDisplay> call, Response<AllRatingsDisplay> response) {
                if (response.isSuccessful()) {
                    List<RatingCommentDisplayDTO> updatedComments = response.body().getAllRatingComments();
                    adapter.setComments(updatedComments); // Postavi nove komentare u adapter
                } else {
                    Log.e("API Error", "Failed to fetch comments: " + response.message());
                    // Handle the error, for example, show a toast message
                }
            }

            @Override
            public void onFailure(Call<AllRatingsDisplay> call, Throwable t) {
                Log.e("API Error", "Failed to fetch comments", t);
                // Handle the error, for example, show a toast message
            }
        });
    }


}
