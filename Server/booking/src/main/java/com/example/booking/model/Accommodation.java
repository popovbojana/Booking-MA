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
}
