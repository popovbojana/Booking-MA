package com.example.booking_ma.adapters;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.AccommodationDetailsScreen;
import com.example.booking_ma.AccountScreen;
import com.example.booking_ma.R;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.model.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {

    private List<Accommodation> accommodations;
    private Context context;

    public AccommodationAdapter(Context context, List<Accommodation> accommodations) {
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
        Accommodation item = accommodations.get(position);

        holder.accommodationImage.setImageResource(item.getImageResource());
        holder.accommodationName.setText(item.getName());
        holder.accommodationPrice.setText(String.valueOf(item.getPrice()));
        holder.accommodationStars.setRating(item.getStars());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AccommodationDetailsScreen.class);

                List<Comment> commentsList = new ArrayList<>(item.getComments());
                List<LocalDate> reservedDatesList = new ArrayList<>(item.getReservedDates());
                List<LocalDate> freeDatesList = new ArrayList<>(item.getFreeDates());
                List<String> amentitesList = new ArrayList<>(item.getAmentites());

                ArrayList<Comment> commenstArrayList = new ArrayList<>(commentsList);
                ArrayList<LocalDate> reservedDatesArrayList = new ArrayList<>(reservedDatesList);
                ArrayList<LocalDate> freeDatesArrayList = new ArrayList<>(freeDatesList);
                ArrayList<String> amentitesArrayList = new ArrayList<>(amentitesList);

                item.getName();
                intent.putExtra("a_name", item.getName());
                intent.putExtra("a_img", item.getImageResource());
                intent.putExtra("a_description", item.getDescription());
                intent.putExtra("a_comments", commenstArrayList);
                intent.putExtra("a_location_name", item.getAddress());
                intent.putExtra("a_location_lat", item.getLatitude());
                intent.putExtra("a_location_long", item.getLongitude());
                intent.putExtra("a_reserved_dates", reservedDatesArrayList);
                intent.putExtra("a_free_dates", freeDatesArrayList);
                intent.putExtra("a_amentities", amentitesArrayList);
                intent.putExtra("a_price", item.getPrice());
                intent.putExtra("a_stars", item.getStars());

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
