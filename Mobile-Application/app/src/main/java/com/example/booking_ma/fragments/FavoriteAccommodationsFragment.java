package com.example.booking_ma.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.FavoriteAccommodationsAdapter;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAccommodationsFragment  extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private TextView averageRating;
    private LinearLayoutManager layoutManager;
    private FavoriteAccommodationsAdapter adapter;
    private Long guestsId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_accommodations, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewAccommodations);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        guestsId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getAllFavoriteAccommodations(token, guestsId);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllFavoriteAccommodations(String jwtToken, Long id) {
        Call<List<AccommodationDisplayDTO>> call = ServiceUtils.accommodationService(jwtToken).getAllFavoritesForGuest(id);

        call.enqueue(new Callback<List<AccommodationDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationDisplayDTO>> call, Response<List<AccommodationDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationDisplayDTO> favAccommodations = response.body();
                    FavoriteAccommodationsAdapter adapter = new FavoriteAccommodationsAdapter(getContext(), favAccommodations, token, guestsId);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
