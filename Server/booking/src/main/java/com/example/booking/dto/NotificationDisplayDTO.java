package com.example.booking.dto;

import com.example.booking.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDisplayDTO {

    private Long id;
    private Long receiverId;
    private Long senderId;
    private String message;
    private NotificationType notificationType;
    private String timestamp;
    private boolean received;
}
