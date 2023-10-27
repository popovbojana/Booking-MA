package com.example.booking_ma.adapters;
import android.content.Context;
import android.content.Intent;
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
        holder.accommodationPrice.setText(item.getPrice());
        holder.accommodationStars.setRating(item.getStars());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AccommodationDetailsScreen.class);

                intent.putExtra("a_name", "a_name");
                intent.putExtra("a_img", "a_img");
                intent.putExtra("a_description", "a_description");
                intent.putExtra("a_comments", "a_comments");
                intent.putExtra("a_location_name", "a_location_name");
                intent.putExtra("a_location_lat", "a_location_lat");
                intent.putExtra("a_location_long", "a_location_long");
                intent.putExtra("a_reserved_dates", "a_reserved_dates");
                intent.putExtra("a_free_dates", "a_free_dates");
                intent.putExtra("a_amentities", "a_amentities");

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
