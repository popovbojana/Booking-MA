package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationChangesDTO {

    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;

}
