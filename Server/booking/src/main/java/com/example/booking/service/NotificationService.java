package com.example.booking.service;

import com.example.booking.model.Notification;
import com.example.booking.repository.*;
import com.example.booking.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> getLastUnreceivedByReceiverId(Long receiverId){
        return this.notificationRepository.findLastUnreceivedByReceiverId(receiverId);
    }
}
