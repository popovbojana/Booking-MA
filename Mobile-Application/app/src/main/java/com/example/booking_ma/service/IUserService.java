package com.example.booking_ma.service;

import com.example.booking_ma.DTO.LoginDTO;
import com.example.booking_ma.DTO.NewUserDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.TokenDTO;
import com.example.booking_ma.DTO.UserDisplayDTO;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET(ServiceUtils.user + "/all-users")
    Call<UserDisplayDTO> getAllUsers();

    @POST(ServiceUtils.user + "/registration")
    Call<ResponseMessage> registration(@Body NewUserDTO newUser);

    @POST(ServiceUtils.user + "/login")
    Call<TokenDTO> login(@Body LoginDTO loginDTO);

}