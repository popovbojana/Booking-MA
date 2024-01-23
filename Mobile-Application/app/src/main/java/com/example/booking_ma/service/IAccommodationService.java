package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AccommodationChangeDisplayDTO;
import com.example.booking_ma.DTO.AccommodationChangesDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.FavoriteAccommodationDTO;
import com.example.booking_ma.DTO.NewAccommodationDTO;
import com.example.booking_ma.DTO.NewAvailabilityPriceDTO;
import com.example.booking_ma.DTO.ReportAccommodationDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST(ServiceUtils.accommodation + "/change-accommodation/{id}")
    Call<ResponseMessage> changeAccommodation(@Path("id") Long id, @Body AccommodationChangesDTO changes);

    @GET(ServiceUtils.accommodation + "/all-new-accommodation")
    Call<List<AccommodationDisplayDTO>> getAllNewAccommodations();

    @GET(ServiceUtils.accommodation + "/all-changes-accommodation")
    Call<List<AccommodationChangeDisplayDTO>> getAllAccommodationChanges();

    @PUT(ServiceUtils.accommodation + "/approval-new-accommodation/{id}")
    Call<ResponseMessage> approveNewAccommodation(@Path("id") Long id, @Body ApprovalDTO approvalDTO);

    @PUT(ServiceUtils.accommodation + "/approval-changes-accommodation/{id}")
    Call<ResponseMessage> approveAccommodationChanges(@Path("id") Long id, @Body ApprovalDTO approvalDTO);

    @POST(ServiceUtils.accommodation + "/new-availability-price/{id}")
    Call<ResponseMessage> addAvailabilityPrice(@Path("id") Long id, @Body NewAvailabilityPriceDTO newAvailabilityPriceDTO);

    @PUT(ServiceUtils.accommodation + "/add-to-favorites")
    Call<ResponseMessage> addToFavorites(@Body FavoriteAccommodationDTO favAccommodation);

    @PUT(ServiceUtils.accommodation + "/remove-from-favorites")
    Call<ResponseMessage> removeFromFavorites(@Body FavoriteAccommodationDTO favAccommodation);

    @GET(ServiceUtils.accommodation + "/all-favorites/{id}")
    Call<List<AccommodationDisplayDTO>> getAllFavoritesForGuest(@Path("id") Long id);

    @GET(ServiceUtils.accommodation + "/availabilities-prices/{accommodatioId}")
    Call<List<AvailabilityDisplayDTO>> getAvailabilitiesPricesBuAccommodatioId(@Path("accommodatioId") Long accommodatioId);

    @PUT(ServiceUtils.accommodation + "/handle-auto-approve/{accommodatioId}")
    Call<ResponseMessage> handleAutoApprove(@Path("accommodatioId") Long accommodatioId);

}