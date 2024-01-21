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

import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.HostCommentsAdapter;
import com.example.booking_ma.service.ServiceUtils;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostCommentsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private TextView averageRating;
    private LinearLayoutManager layoutManager;
    private HostCommentsAdapter adapter;
    private Long ownersId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_comments, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewAccommodations);
        averageRating = view.findViewById(R.id.textViewAverageRating);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        ownersId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getAllComments(token, ownersId);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllComments(String jwtToken, Long id) {
        Call<AllRatingsDisplay> call = ServiceUtils.ratingCommentService(jwtToken).getAllForOwner(id);

        call.enqueue(new Callback<AllRatingsDisplay>() {
            @Override
            public void onResponse(Call<AllRatingsDisplay> call, Response<AllRatingsDisplay> response) {
                if (response.isSuccessful()) {
                    AllRatingsDisplay comments = response.body();
                    averageRating.setText("Average rating: " + comments.getAverageRating());
                    HostCommentsAdapter adapter = new HostCommentsAdapter(getContext(), comments.getAllRatingComments(), token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AllRatingsDisplay> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
