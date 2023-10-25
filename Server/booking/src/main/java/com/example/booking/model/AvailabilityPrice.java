package com.example.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "availability_prices")
public class AvailabilityPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_id")
    private AccommodationChange accommodationChange;

    private double amount;

    private LocalDateTime dateFrom;

    private LocalDateTime dateUntil;

    public AvailabilityPrice(Accommodation accommodation, AccommodationChange accommodationChange, double amount, LocalDateTime dateFrom, LocalDateTime dateUntil){
        this.accommodation = accommodation;
        this.accommodationChange = accommodationChange;
        this.amount = amount;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }
}
