package com.example.booking.dto;

import com.example.booking.model.enums.PriceType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAccommodationDTO {

    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private List<NewAvailabilityPriceDTO> availability;
    private int cancellationDeadlineInDays;
    private String address;
    private double latitude;
    private double longitude;
    private double finalRating;
    private double standardPrice;

}
