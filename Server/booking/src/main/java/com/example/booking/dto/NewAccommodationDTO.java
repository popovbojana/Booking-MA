package com.example.booking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAccommodationDTO {

    private String name;
    private String description;
    private String location;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private String availableFrom;
    private String availableUntil;
    private double pricePerNight;

}
