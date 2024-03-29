package com.example.booking.dto;

import com.example.booking.model.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationChangeDisplayDTO {
    private Long accommodationId;
    private String newName;
    private String newDescription;
    private String newAmenities;
    private int newMinGuests;
    private int newMaxGuests;
    private String newType;
    private PriceType newPriceType;
    private int newCancellationDeadlineInDays;
    private double newStandardPrice;
    private LocalDateTime newDateFrom;
    private LocalDateTime newDateUntil;
    private double newAmount;
    private boolean autoApprove;

}
