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
import com.example.uberapp_tim22.DTO.ChatMessagesDTO;
import com.example.uberapp_tim22.DTO.DriverVehicleDTO;
import com.example.uberapp_tim22.DTO.HopInInboxReturnedDTO;
import com.example.uberapp_tim22.DTO.HopInMessageDTO;
import com.example.uberapp_tim22.DTO.HopInMessageReturnedDTO;
import com.example.uberapp_tim22.DTO.MessDTO;
import com.example.uberapp_tim22.DTO.ResponseChatDTO;

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
                if (method.equals("getMessage")) {
                    Long myId = (Long) extras.get("myId");
                    Long otherId = (Long) extras.get("otherId");
                    getMessages(myId, otherId);
                }
                if (method.equals("getNotification")) {
                    Long receiverId = (Long) extras.get("receiverId");
                    Long senderId = (Long) extras.get("senderId");
                    getNotification(receiverId, senderId);
                }

                else if (method.equals("sendMessage")) {
                    ChatMessagesDTO message = (ChatMessagesDTO) extras.get("message");
                    sendMessage(message);
                }
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

    private void getNotification(Long receiverId, String jwtToken) {
        Call<NotificationDisplayDTO> call = ServiceUtils.notificationService(jwtToken).getLastUnreceivedByReceiverId(receiverId);
        call.enqueue(new Callback<NotificationDisplayDTO>() {

            @Override
            public void onResponse(Call<NotificationDisplayDTO> call, Response<NotificationDisplayDTO> response){
                if (response.body() != null) {
                    NotificationDisplayDTO notification = response.body();

                    Log.i("Notification", notification.getMessage().toString());
                    getNotificationsBroadcast(chat);

                }
                else {
                    Log.d("MESS", "SENDING ERROR");
                }
            }

            @Override
            public void onFailure(Call<NotificationDisplayDTO> call, Throwable t) {
                Log.d("EMAIL_REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });
    }

//    private void getMessages(Long receiverId) {
//        Call<List<ResponseChatDTO>> call = ServiceUtils.chatService.getChatsOfUser(myId);
//        call.enqueue(new Callback<List<ResponseChatDTO>>() {
//
//            @Override
//            public void onResponse(Call<List<ResponseChatDTO>> call, Response<List<ResponseChatDTO>> response){
//                if (response.body() != null) {
//                    List<ResponseChatDTO> responseChats = response.body();
//
//                    ResponseChatDTO chat = new ResponseChatDTO();
//                    for(ResponseChatDTO responseChat: responseChats){
//                        if(otherId == responseChat.getOtherId()){
//                            chat = responseChat;
//                        }
//                    }
//
//                    getMessagesBroadcast(chat);
//                }
//                else {
//                    Log.d("MESS", "SENDING ERROR");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ResponseChatDTO>> call, Throwable t) {
//                Log.d("EMAIL_REZ", t.getMessage() != null ? t.getMessage() : "error");
//            }
//        });
//    }

//    private void getDriverLocationBroadcast(Double latitude, Double longitude, String address){
//        Intent intent = new Intent("mapActivity");
//        intent.putExtra("latitudeBroadcast", latitude);
//        intent.putExtra("longitudeBroadcast", longitude);
//        intent.putExtra("addressBroadcast", address);
//
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }

-
    private void getNotificationsBroadcast(NotificationDisplayDTO dto){
        Intent intent = new Intent("chatActivity");
        intent.putExtra("messages", dto);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
