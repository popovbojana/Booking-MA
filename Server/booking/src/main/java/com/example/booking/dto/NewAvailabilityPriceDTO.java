package com.example.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAvailabilityPriceDTO {

    private double amount;

    private LocalDateTime dateFrom;

    private LocalDateTime dateUntil;
}
