package com.example.booking.model;

import com.example.booking.dto.AccommodationDisplayDTO;
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
@Table(name = "accommodations")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AvailabilityPrice> availabilities;

    private int cancellationDeadlineInDays;

    private boolean approved;

    private boolean hasChanges;

    @OneToOne
    @JoinColumn(name = "change_id")
    private AccommodationChange accommodationChange;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<RatingComment> ratingComments;

    private String address;

    private double latitude;

    private double longitude;

    private double finalRating;

    private double standardPrice;


    public Accommodation(Owner owner, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays, String address, double latitude, double longitude, double finalRating, double standardPrice){
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.approved = false;
        this.hasChanges = false;
        this.accommodationChange = null;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalRating = finalRating;
        this.standardPrice = standardPrice;
    }

    public AccommodationDisplayDTO parseToDisplay() {
        List<AvailabilityDisplayDTO> availabilityDisplayDTOS = new ArrayList<>();
        for (AvailabilityPrice ap : availabilities){
            availabilityDisplayDTOS.add(new AvailabilityDisplayDTO(ap.getAmount(), ap.getDateFrom(), ap.getDateUntil()));
        }
        return new AccommodationDisplayDTO(id, owner.getId(), name, description, amenities, minGuests, maxGuests, type, priceType, availabilityDisplayDTOS, cancellationDeadlineInDays, approved, hasChanges, address, longitude, latitude, finalRating, standardPrice);
    }
}
