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
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.GuestApprovedReservationsAdapter;
import com.example.booking_ma.adapters.GuestDeniedReservationsAdapter;
import com.example.booking_ma.adapters.GuestPendingReservationsAdapter;
import com.example.booking_ma.adapters.ReportedGuestsAdapter;
import com.example.booking_ma.adapters.UnapprovedCommentsAdapter;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestDeniedReservationsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private UnapprovedCommentsAdapter adapter;
    private Long guestId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_denied_reservation, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewGuestDeniedReservation);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        guestId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getAllDeniedReservations(token);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllDeniedReservations(String jwtToken) {
        Call<List<ReservationDisplayDTO>> call = ServiceUtils.reservationService(token).getGuestDeniedReservations(guestId);

        call.enqueue(new Callback<List<ReservationDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<ReservationDisplayDTO>> call, Response<List<ReservationDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<ReservationDisplayDTO> guestDeniedReservations = response.body();

                    GuestDeniedReservationsAdapter adapter = new GuestDeniedReservationsAdapter(getContext(), guestDeniedReservations, token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ReservationDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
