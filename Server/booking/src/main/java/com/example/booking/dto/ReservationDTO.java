package com.example.booking.dto;

import com.example.booking.model.enums.ReservationState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long guestId;
    private Long ownerId;
    private Long accommodationId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int guestsNumber;
    private float totalCost;
}
