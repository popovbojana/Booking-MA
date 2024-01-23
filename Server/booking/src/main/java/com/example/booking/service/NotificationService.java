package com.example.booking.service;

import com.example.booking.dto.NotificationDTO;
import com.example.booking.model.Notification;
import com.example.booking.repository.*;
import com.example.booking.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        List<Notification> unreadNotifications = notificationRepository.findUnreceivedByReceiverId(receiverId);

        // Select the first item from the list, if it exists
        Optional<Notification> notification = unreadNotifications.isEmpty()
                ? Optional.empty()
                : Optional.of(unreadNotifications.get(0));

        Notification not = notification.get();
        not.setReceived(true);
        this.notificationRepository.save(not);
        return notification;
    }

    @Override
    public void createNotification(NotificationDTO dto){
        Notification notification = new Notification(dto.getReceiverId(), dto.getSenderId(), dto.getMessage(), dto.getNotificationType(), LocalDateTime.now(), dto.isReceived());
        this.notificationRepository.save(notification);

    }
}
