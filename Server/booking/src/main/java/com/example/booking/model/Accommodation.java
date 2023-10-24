package com.example.booking.model;

import com.example.booking.dto.AccommodationDisplayDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Owner owner;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    private String availableFrom;

    private String availableUntil;

    private double pricePerNight;

    private boolean approved;

    private boolean hasChanges;

    @OneToOne
    @JoinColumn(name = "change_id")
    private AccommodationChange accommodationChange;


    public Accommodation(Owner owner, String name, String description, String amenities, int minGuests, int maxGuests, String type, String availableFrom, String availableUntil, double pricePerNight){
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.pricePerNight = pricePerNight;
        this.approved = false;
        this.hasChanges = false;
        this.accommodationChange = null;
    }

    public AccommodationDisplayDTO parseToDisplay() {
        return new AccommodationDisplayDTO(id, owner.getId(), name, description, amenities, minGuests, maxGuests, availableFrom, availableUntil, pricePerNight, approved, hasChanges);
    }
}
