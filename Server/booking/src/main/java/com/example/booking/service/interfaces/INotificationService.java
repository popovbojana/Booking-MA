package com.example.booking.service.interfaces;

import com.example.booking.dto.NotificationDTO;
import com.example.booking.dto.NotificationDisplayDTO;
import com.example.booking.model.Notification;

import java.util.List;
import java.util.Optional;

public interface INotificationService {
    Optional<Notification> getLastUnreceivedByReceiverId(Long receiverId);

    List<NotificationDisplayDTO> getAllByReceiverId(Long receiverId);

    void createNotification(NotificationDTO dto);
}
