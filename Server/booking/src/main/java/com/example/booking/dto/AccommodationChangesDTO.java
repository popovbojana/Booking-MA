package com.example.booking.dto;

import com.example.booking.model.AvailabilityPrice;
import com.example.booking.model.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationChangesDTO {

    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private int cancellationDeadlineInDays;
    private double standardPrice;
    private LocalDateTime dateFrom;
    private LocalDateTime dateUntil;
    private double amount;
    private boolean autoApprove;

}
