package com.example.booking_ma.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.AccommodationChangeDisplayDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.R;

import com.example.booking_ma.adapters.AccommodationChangesAdapter;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationChangesFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private AccommodationChangesAdapter adapter;
    private List<Accommodation> allAccommodations;
    private Long ownersId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodations, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewAccommodations);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        ownersId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getAllAccommodationChanges(token);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllAccommodationChanges(String jwtToken) {
        Call<List<AccommodationChangeDisplayDTO>> call = ServiceUtils.accommodationService(jwtToken).getAllAccommodationChanges();

        call.enqueue(new Callback<List<AccommodationChangeDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationChangeDisplayDTO>> call, Response<List<AccommodationChangeDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationChangeDisplayDTO> accommodations = response.body();

                    AccommodationChangesAdapter adapter = new AccommodationChangesAdapter(getContext(), accommodations, token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationChangeDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
