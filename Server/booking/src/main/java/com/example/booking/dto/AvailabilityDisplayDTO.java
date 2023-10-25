package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDisplayDTO {
    private double amount;
    private LocalDateTime dateFrom;

    private LocalDateTime dateUntil;
}
