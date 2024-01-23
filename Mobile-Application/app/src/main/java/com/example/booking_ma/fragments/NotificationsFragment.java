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

import com.example.booking_ma.DTO.NotificationDisplayDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.GuestPendingReservationsAdapter;
import com.example.booking_ma.adapters.NotificationsAdapter;
import com.example.booking_ma.adapters.OwnerApprovedReservationsAdapter;
import com.example.booking_ma.adapters.OwnerPendingReservationsAdapter;
import com.example.booking_ma.adapters.ReportedGuestsAdapter;
import com.example.booking_ma.adapters.UnapprovedCommentsAdapter;
import com.example.booking_ma.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private UnapprovedCommentsAdapter adapter;
    private Long myId;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewNotifications);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        getNotificationsByReceiverId(token);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getNotificationsByReceiverId(String jwtToken) {
        Call<List<NotificationDisplayDTO>> call = ServiceUtils.notificationService(token).getAllByReceiverId(myId);

        call.enqueue(new Callback<List<NotificationDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationDisplayDTO>> call, Response<List<NotificationDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<NotificationDisplayDTO> notifications = response.body();

                    NotificationsAdapter adapter = new NotificationsAdapter(getContext(), notifications, token);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
}
