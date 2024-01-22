package com.example.booking_ma.service;

import android.util.Log;

import com.example.booking_ma.tools.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

//    private static final String SERVICE_API_PATH = "http://192.168.1.8:8081/api/";
    private static final String SERVICE_API_PATH = "http://192.168.1.14:8081/api/";

    protected static final String accommodation = "accommodation";
    protected static final String user = "user";
    protected static final String ratingComment = "rating-comment";
    protected static final String reservations = "reservations";
    protected static final String notifications = "notifications";


    public static OkHttpClient.Builder httpClientBuilder(String authToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        AuthInterceptor authInterceptor = new AuthInterceptor(authToken);
        builder.addInterceptor(authInterceptor);

        return builder;
    }

    public static Retrofit retrofit(String authToken) {
        return new Retrofit.Builder()
                .baseUrl(SERVICE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create(getCustomGson()))
                .client(httpClientBuilder(authToken).build())
                .build();
    }

    private static Gson getCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return gsonBuilder.create();
    }

    public static IAccommodationService accommodationService(String authToken) {
        return retrofit(authToken).create(IAccommodationService.class);
    }

    public static IUserService userService(String authToken) {
        return retrofit(authToken).create(IUserService.class);
    }

    public static IRatingCommentService ratingCommentService(String authToken) {
        return retrofit(authToken).create(IRatingCommentService.class);
    }

    public static IReservationService reservationService(String authToken) {
        return retrofit(authToken).create(IReservationService.class);
    }

    public static INotificationService notificationService(String authToken) {
        return retrofit(authToken).create(INotificationService.class);
    }
}
