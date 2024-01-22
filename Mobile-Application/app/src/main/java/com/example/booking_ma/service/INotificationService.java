package com.example.booking_ma.service;

import com.example.booking_ma.DTO.NotificationDisplayDTO;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface INotificationService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.reservations + "/{receiverId}")
    Call<NotificationDisplayDTO> getLastUnreceivedByReceiverId(@Path("receiverId") Long receiverId);

}
