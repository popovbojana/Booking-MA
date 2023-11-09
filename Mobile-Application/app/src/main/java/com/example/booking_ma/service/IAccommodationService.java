package com.example.booking_ma.service;

import com.example.booking_ma.DTO.UserDisplayDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface IAccommodationService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.user + "/all-users")
    Call<UserDisplayDTO> getAllUsers();

}