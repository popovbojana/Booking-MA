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
import com.example.booking_ma.adapters.ReportedOwnersCommentsAdapter;
import com.example.booking_ma.adapters.UnapprovedCommentsAdapter;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedOwnersCommentsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private UnapprovedCommentsAdapter adapter;
    private Long ownersId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_reports, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewUsersReports);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        ownersId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getAllReportedOwnersComments(token);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllReportedOwnersComments(String jwtToken) {
        Call<List<RatingCommentDisplayDTO>> call = ServiceUtils.ratingCommentService(jwtToken).getReportedOwnersComments();

        call.enqueue(new Callback<List<RatingCommentDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<RatingCommentDisplayDTO>> call, Response<List<RatingCommentDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<RatingCommentDisplayDTO> reportedOwnersComments = response.body();

                    ReportedOwnersCommentsAdapter adapter = new ReportedOwnersCommentsAdapter(getContext(), reportedOwnersComments, token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RatingCommentDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
