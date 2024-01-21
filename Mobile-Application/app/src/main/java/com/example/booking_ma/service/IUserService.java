package com.example.booking_ma.service;

import com.example.booking_ma.DTO.ChangePasswordDTO;
import com.example.booking_ma.DTO.LoginDTO;
import com.example.booking_ma.DTO.NewUserDTO;
import com.example.booking_ma.DTO.ReportedUserReasonDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.TokenDTO;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.DTO.UserPasswordDTO;
import com.example.booking_ma.DTO.UserUpdateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.user + "/user-display/{userId}")
    Call<UserDisplayDTO> getUserDisplay(@Path("userId") Long userId);

    @GET(ServiceUtils.user + "/all-users")
    Call<List<UserDisplayDTO>> getAllUsers();

    @POST(ServiceUtils.user + "/registration")
    Call<ResponseMessage> registration(@Body NewUserDTO newUser);

    @PUT(ServiceUtils.user + "/update-user/{userId}")
    Call<ResponseMessage> updateUser(@Path("userId") Long userId, @Body UserUpdateDTO u);

    @POST(ServiceUtils.user + "/login")
    Call<TokenDTO> login(@Body LoginDTO loginDTO);

    @PUT(ServiceUtils.user + "/change-password/{userId}")
    Call<ResponseMessage> changePassword(@Path("userId") Long userId, @Body ChangePasswordDTO u);

    @PUT(ServiceUtils.user + "/check-password/{userId}")
    Call<Boolean> checkUserPassword(@Path("userId") Long userId, @Body UserPasswordDTO userPassword);

    @DELETE(ServiceUtils.user + "/delete-user/{userId}")
    Call<ResponseMessage> deleteUser(@Path("userId") Long userId);

    @PUT(ServiceUtils.user + "/report-guest/{userId}")
    Call<ResponseMessage> reportGuest(@Path("userId") Long userId, @Body ReportedUserReasonDTO reason);

    @PUT(ServiceUtils.user + "/report-owner/{userId}")
    Call<ResponseMessage> reportOwner(@Path("userId") Long userId, @Body ReportedUserReasonDTO reason);
    

}