package com.example.booking_ma.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.booking_ma.DTO.NotificationDisplayDTO;
import com.example.booking_ma.DTO.RatingCommentDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.model.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("pref_accessToken", "");
        String method = (String) extras.get("method");


        executor.execute(new Runnable() {
            @Override
            public void run() {
//                if (method.equals("getMessage")) {
//                    Long myId = (Long) extras.get("myId");
//                    Long otherId = (Long) extras.get("otherId");
//                    getMessages(myId, otherId);
//                }
                if (method.equals("getNotifications")) {
                    Long receiverId = (Long) extras.get("receiverId");
                    getNotifications(receiverId, token);
                }

//                else if (method.equals("sendMessage")) {
//                    ChatMessagesDTO message = (ChatMessagesDTO) extras.get("message");
//                    sendMessage(message);
//                }
            }
        });
        stopSelf();
        return START_NOT_STICKY;
    }


//    private void sendMessage(ChatMessagesDTO message) {
//
//        Call<MessDTO> call = ServiceUtils.chatService.sendMessageToChat(message);
//        call.enqueue(new Callback<MessDTO>() {
//
//            @Override
//            public void onResponse(Call<MessDTO> call, Response<MessDTO> response){
//                if (response.body() != null)
//                    Log.d("MESS", response.body().toString());
//                else
//                    Log.d("MESS", "SENDING ERROR");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMessage> call, Throwable t) {
//                Log.d("EMAIL_REZ", t.getMessage() != null ? t.getMessage() : "error");
//            }
//        });
//    }

    private void getNotifications(Long receiverId, String jwtToken) {
        Call<NotificationDisplayDTO> call = ServiceUtils.notificationService(jwtToken).getLastUnreceivedByReceiverId(receiverId);
//        getNotificationsBroadcast(new NotificationDisplayDTO(1L, 1L, 1L, "a", NotificationType.CANCELED_RESERVATION, LocalDateTime.now(), false));


        call.enqueue(new Callback<NotificationDisplayDTO>() {

            @Override
            public void onResponse(Call<NotificationDisplayDTO> call, Response<NotificationDisplayDTO> response){
                if (response.body() != null) {
                    NotificationDisplayDTO notification = response.body();

                    Log.i("Notification", notification.getMessage().toString());
                    getNotificationsBroadcast(notification);

                }
                else {
                    Log.d("MESS", "SENDING ERROR");
                    getNotificationsBroadcast(new NotificationDisplayDTO(0L, 0L, 0L, "", NotificationType.CANCELED_RESERVATION, LocalDateTime.now(), false));
                }
            }

            @Override
            public void onFailure(Call<NotificationDisplayDTO> call, Throwable t) {
                Log.d("EMAIL_REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });
    }


    private void getNotificationsBroadcast(NotificationDisplayDTO dto){
        Intent intent = new Intent("notificationActivity");
        intent.putExtra("notifications", dto);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
