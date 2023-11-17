package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AccommodationChangesDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.NewAccommodationDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAccommodationService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.accommodation + "/all-accommodation")
    Call<List<AccommodationDisplayDTO>> getAllAccommodations();

    @GET(ServiceUtils.accommodation + "/all-accommodation/{ownersId}")
    Call<List<AccommodationDisplayDTO>> getAllAccommodationForOwner(@Path("ownersId") Long ownersId);

    @POST(ServiceUtils.accommodation + "/add-new/{ownersId}")
    Call<ResponseMessage> addNewAccommodation(@Path("ownersId") Long ownersId, @Body NewAccommodationDTO newAccommodation);

    @GET(ServiceUtils.accommodation + "/accommodation/{id}")
    Call<AccommodationDisplayDTO> getAccommodationById(@Path("id") Long id);

    @POST(ServiceUtils.accommodation + "change-accommodation/{id}")
    Call<ResponseMessage> changeAccommodation(@Path("id") Long id, @Body AccommodationChangesDTO changes);
}