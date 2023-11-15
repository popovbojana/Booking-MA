package com.example.booking_ma.service;

import com.example.booking_ma.tools.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

    //OVDE UNESI SVOJ LOKALHOST (MOJ JE 192.168.1.24) u cmd kucas ipconfig
    //fica
    private static final String SERVICE_API_PATH = "http://192.168.1.24:8081/api/";
    //bojana
//    private static final String SERVICE_API_PATH = "http://172.20.10.4:8081/api/";

    protected static final String accommodation = "accommodation";
    protected static final String user = "user";
    protected static final String ratingComment = "rating-comment";

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create(getCustomGson()))
            .client(test())
            .build();

    private static Gson getCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return gsonBuilder.create();
    }

    public static IAccommodationService accommodationService = retrofit.create(IAccommodationService.class);
    public static IUserService userService = retrofit.create(IUserService.class);
    public static IRatingCommentService ratingCommentService = retrofit.create(IRatingCommentService.class);

}
