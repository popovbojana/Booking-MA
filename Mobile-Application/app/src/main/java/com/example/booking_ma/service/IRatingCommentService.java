package com.example.booking_ma.service;

import com.example.booking_ma.DTO.AllRatingsDisplay;
import com.example.booking_ma.DTO.ApprovalDTO;
import com.example.booking_ma.DTO.RateCommentDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
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

public interface IRatingCommentService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.ratingComment + "/all-for-accommodation/{id}")
    Call<AllRatingsDisplay> getAllForAccommodation(@Path("id") Long id);

    @GET(ServiceUtils.ratingComment + "/all-for-owner/{id}")
    Call<AllRatingsDisplay> getAllForOwner(@Path("id") Long id);

    @PUT(ServiceUtils.ratingComment + "/report/{id}")
    Call<ResponseMessage> report(@Path("id") Long id);

    @GET(ServiceUtils.ratingComment + "/all-unapproved")
    Call<List<RatingCommentDisplayDTO>> getAllUnapproved();

    @PUT(ServiceUtils.ratingComment + "/approve/{id}")
    Call<ResponseMessage> approve(@Path("id") Long id, @Body ApprovalDTO approvalDTO);

    @POST(ServiceUtils.ratingComment + "/add-new")
    Call<ResponseMessage> rateComment(@Body RateCommentDTO rateCommentDTO);

    @GET(ServiceUtils.user + "/all-reported-owners-comments")
    Call<List<RatingCommentDisplayDTO>> getReportedOwnersComments();

    @PUT(ServiceUtils.user + "/handle-reported-comment/{ratingCommentId}")
    Call<ResponseMessage> handleReportedComment(@Path("id") Long id, @Body ApprovalDTO approvalDTO);
}