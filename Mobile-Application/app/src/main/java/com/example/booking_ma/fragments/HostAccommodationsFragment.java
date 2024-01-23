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

import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.adapters.HostAccommodationsAdapter;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.service.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostAccommodationsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private HostAccommodationsAdapter adapter;
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

        getAllAccommodations(token, ownersId);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllAccommodations(String jwtToken, Long id) {
        Call<List<AccommodationDisplayDTO>> call = ServiceUtils.accommodationService(jwtToken).getAllAccommodationForOwner(id);

        call.enqueue(new Callback<List<AccommodationDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationDisplayDTO>> call, Response<List<AccommodationDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationDisplayDTO> accommodations = response.body();
                    for(AccommodationDisplayDTO a : accommodations){
                        Log.i("accc", String.valueOf(a.isAutoApproved()));
                    }
                    HostAccommodationsAdapter adapter = new HostAccommodationsAdapter(getContext(), accommodations, token);
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
