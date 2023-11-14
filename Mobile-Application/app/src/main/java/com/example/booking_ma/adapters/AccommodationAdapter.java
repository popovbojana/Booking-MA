package com.example.booking_ma.adapters;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationDetailsScreen;
import com.example.booking_ma.AccountScreen;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.model.RatingComment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {

    private List<AccommodationDisplayDTO> accommodations;
    private Context context;

    public AccommodationAdapter(Context context, List<AccommodationDisplayDTO> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccommodationDisplayDTO item = accommodations.get(position);

//        holder.accommodationImage.setImageResource(item.getImageResource());
        holder.accommodationName.setText(item.getName());
        holder.accommodationPrice.setText(String.valueOf(999.99));
        holder.accommodationStars.setRating(Float.valueOf(String.valueOf(item.getFinalRating())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AccommodationDetailsScreen.class);

                ArrayList<Comment> commenstArrayList = new ArrayList<>();
                ArrayList<LocalDate> reservedDatesArrayList = new ArrayList<>();
                ArrayList<LocalDate> freeDatesArrayList= new ArrayList<>();
                ArrayList<String> amentitesArrayList= new ArrayList<>();

                intent.putExtra("a_name", item.getName());
                intent.putExtra("a_img", R.drawable.item);
                intent.putExtra("a_description", item.getDescription());
                intent.putExtra("a_price", 999.99);
                intent.putExtra("a_stars", item.getFinalRating());
                intent.putExtra("a_location_name", item.getAddress());
                intent.putExtra("a_location_lat", item.getLatitude());
                intent.putExtra("a_location_long", item.getLongitude());
                intent.putExtra("a_amentites", item.getAmenities());


//                if (item.getRatingComments() != null && !item.getRatingComments().isEmpty()) {
//                    ArrayList<RatingComment> commentsList = new ArrayList<>(item.getRatingComments());
//                    intent.putExtra("a_comments",  (Serializable) commentsList);
//                }
//
//                if (item.getReservedDates() != null && !item.getReservedDates().isEmpty()) {
//                    List<LocalDate> reservedDatesList = new ArrayList<>(item.getReservedDates());
//                    reservedDatesArrayList = new ArrayList<>(reservedDatesList);
//                    intent.putExtra("a_reserved_dates", reservedDatesArrayList);
//                }
//
//                if (item.getFreeDates() != null && !item.getFreeDates().isEmpty()) {
//                    List<LocalDate> freeDatesList = new ArrayList<>(item.getFreeDates());
//                    freeDatesArrayList = new ArrayList<>(freeDatesList);
//                    intent.putExtra("a_free_dates", freeDatesArrayList);
//                }
//

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
        TextView accommodationName;
        TextView accommodationPrice;
        RatingBar accommodationStars;

        public ViewHolder(View itemView) {
            super(itemView);
            accommodationImage = itemView.findViewById(R.id.accommodationImage);
            accommodationName = itemView.findViewById(R.id.accommodationName);
            accommodationPrice = itemView.findViewById(R.id.accommodationPrice);
            accommodationStars = itemView.findViewById(R.id.accommodationStars);
        }
    }
}
