package com.example.booking.model;

import com.example.booking.dto.AvailabilityDisplayDTO;
import com.example.booking.dto.NotificationDisplayDTO;
import com.example.booking.model.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long receiverId;
    private Long senderId;
    private Long message;
    private NotificationType notificationType;
    private LocalDateTime timestamp;
    private boolean received;

    public Notification(Long id, Long receiverId, Long senderId, Long message, NotificationType notificationType, LocalDateTime timestamp, boolean received) {
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

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
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

    public NotificationDisplayDTO parseToDisplay() {
        return new NotificationDisplayDTO(id, receiverId, senderId, message, notificationType, timestamp, received);
    }

}
