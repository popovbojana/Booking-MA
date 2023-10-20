package com.example.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    }
}
