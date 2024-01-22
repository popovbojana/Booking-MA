package com.example.booking.service.interfaces;

import com.example.booking.model.Notification;

import java.util.Optional;

public interface INotificationService {
    Optional<Notification> getLastUnreceivedByReceiverId(Long receiverId);
}
