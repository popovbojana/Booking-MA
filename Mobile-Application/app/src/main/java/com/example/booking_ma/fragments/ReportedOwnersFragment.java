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

import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.ReportedGuestsAdapter;
import com.example.booking_ma.adapters.ReportedOwnersAdapter;
import com.example.booking_ma.adapters.UnapprovedCommentsAdapter;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedOwnersFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private UnapprovedCommentsAdapter adapter;
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

        getAllReportedOwners(token);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllReportedOwners(String jwtToken) {
        Call<List<UserDisplayDTO>> call = ServiceUtils.userService(token).getReportedOwners();

        call.enqueue(new Callback<List<UserDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<UserDisplayDTO>> call, Response<List<UserDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<UserDisplayDTO> reportedGuests = response.body();

                    ReportedOwnersAdapter adapter = new ReportedOwnersAdapter(getContext(), reportedGuests, token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UserDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
