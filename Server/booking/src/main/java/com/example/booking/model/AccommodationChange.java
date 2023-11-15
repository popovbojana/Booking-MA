package com.example.booking.model;

import com.example.booking.dto.AccommodationChangeDisplayDTO;
import com.example.booking.dto.AvailabilityDisplayDTO;
import com.example.booking.model.enums.PriceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "accommodation_changes")
public class AccommodationChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "accommodationChange")
    private Accommodation accommodation;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @OneToMany(mappedBy = "accommodationChange")
    private List<AvailabilityPrice> availabilities;

    private int cancellationDeadlineInDays;

    private double standardPrice;

    public AccommodationChange(Accommodation accommodation, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays, double standardPrice){
        this.accommodation = accommodation;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }

    public AccommodationChangeDisplayDTO parseToDisplay() {
        List<AvailabilityDisplayDTO> availabilityDisplayDTOS = new ArrayList<>();
        for (AvailabilityPrice ap : availabilities){
            availabilityDisplayDTOS.add(new AvailabilityDisplayDTO(ap.getAmount(), ap.getDateFrom(), ap.getDateUntil()));
        }
        return new AccommodationChangeDisplayDTO(accommodation.getId(), name, description, amenities, minGuests, maxGuests, type, priceType, availabilityDisplayDTOS, cancellationDeadlineInDays, standardPrice);
    }
}
