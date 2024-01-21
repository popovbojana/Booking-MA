package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAccommodationAvailabilityDTO {
    private Long accommodationId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
