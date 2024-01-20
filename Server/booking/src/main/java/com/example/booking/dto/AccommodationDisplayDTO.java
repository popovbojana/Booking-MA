package com.example.booking.dto;

import com.example.booking.model.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDisplayDTO {

    private Long id;
    private Long ownersId;
    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private List<AvailabilityDisplayDTO> availabilities;
    private int cancellationDeadlineInDays;
    private boolean approved;
    private boolean hasChanges;
    private String address;
    private double latitude;
    private double longitude;
    private double finalRating;
    private double standardPrice;
    private List<ReservationDisplayDTO> reservations;
    private boolean autoApprove;


    public AccommodationDisplayDTO(String name, String description, int minGuests, int maxGuests, String type, PriceType priceType, int cancellationDeadlineInDays, double standardPrice, String address, String amenities, List<ReservationDisplayDTO> reservations, boolean autoApprove){
        this.name = name;
        this.description = description;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.standardPrice = standardPrice;
        this.address = address;
        this.amenities = amenities;
        this.reservations = reservations;
        this.autoApprove = autoApprove;
    }
}
