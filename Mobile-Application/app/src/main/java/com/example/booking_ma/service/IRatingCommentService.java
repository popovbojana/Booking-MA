package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.DTO.UserDisplayDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IRatingCommentService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.ratingComment + "/all-for-accommodation/{id}")
    Call<AllRatingsDisplay> getAllForAccommodation(@Path("id") Long id);

}