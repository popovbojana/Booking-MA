package com.example.booking_ma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.R;

import java.util.List;

public class AccommodationAmentiteAdapter extends RecyclerView.Adapter<AccommodationAmentiteAdapter.ViewHolder> {

    private List<String> amentites;
    private Context context;

    public AccommodationAmentiteAdapter(Context context, List<String> amentites) {
        this.context = context;
        this.amentites = amentites;
    }

    @Override
    public AccommodationAmentiteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amentites, parent, false);
        return new AccommodationAmentiteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccommodationAmentiteAdapter.ViewHolder holder, int position) {
        String item = amentites.get(position);

        holder.amentite.setText(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return amentites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amentite;

        public ViewHolder(View itemView) {
            super(itemView);
            amentite = itemView.findViewById(R.id.textViewAmentite);
        }
    }
}
