package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ReportAccommodationDTO;
import com.example.booking_ma.DTO.ReservationDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IReservationService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.reservations + "/reservation")
    Call<ResponseMessage> createReservation(@Body ReservationDTO reservation);

    @GET(ServiceUtils.reservations + "/report-for-accommodation/{id}")
    Call<ReportAccommodationDTO> getReportForAccommodation(@Path("id") Long id);

}