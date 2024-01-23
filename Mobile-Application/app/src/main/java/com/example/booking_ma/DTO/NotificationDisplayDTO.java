package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.NotificationType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationDisplayDTO implements Serializable {

    private Long id;
    private Long receiverId;
    private Long senderId;
    private String message;
    private NotificationType notificationType;
    private LocalDateTime timestamp;
    private boolean received;

    public NotificationDisplayDTO(Long id, Long receiverId, Long senderId, String message, NotificationType notificationType, LocalDateTime timestamp, boolean received) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
        this.received = received;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
