package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ReservationDTO;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET(ServiceUtils.reservations + "/guest-pending-reservations/{guestId}")
    Call<List<ReservationDisplayDTO>> getGuestPendingReservations(@Path("guestId") Long guestId);

    @GET(ServiceUtils.reservations + "/guest-approved-reservations/{guestId}")
    Call<List<ReservationDisplayDTO>> getGuestApprovedReservations(@Path("guestId") Long guestId);

    @GET(ServiceUtils.reservations + "/guest-denied-reservations/{guestId}")
    Call<List<ReservationDisplayDTO>> getGuestDeniedReservations(@Path("guestId") Long guestId);

    @GET(ServiceUtils.reservations + "/owner-pending-reservations/{ownerId}")
    Call<List<ReservationDisplayDTO>> getOwnerPendingReservations(@Path("ownerId") Long ownerId);

    @GET(ServiceUtils.reservations + "/owner-approved-reservations/{ownerId}")
    Call<List<ReservationDisplayDTO>> getOwnerApprovedReservations(@Path("ownerId") Long ownerId);

    @GET(ServiceUtils.reservations + "/owner-denied-reservations/{ownerId}")
    Call<List<ReservationDisplayDTO>> getOwnerDeniedReservations(@Path("ownerId") Long ownerId);

    @DELETE(ServiceUtils.reservations + "/reservation-delete/{reservationId}")
    Call<ResponseMessage> deleteReservation(@Path("reservationId") Long reservationId);

    @PUT(ServiceUtils.reservations + "/reservation-cancel/{reservationId}")
    Call<ResponseMessage> cancelReservation(@Path("reservationId") Long reservationId);

    @PUT(ServiceUtils.reservations + "/reservation-approve/{reservationId}")
    Call<ResponseMessage> approveReservation(@Path("reservationId") Long reservationId);

    @PUT(ServiceUtils.reservations + "/reservation-deny/{reservationId}")
    Call<ResponseMessage> denyReservation(@Path("reservationId") Long reservationId);

}