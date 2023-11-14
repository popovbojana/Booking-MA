package com.example.booking_ma.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AvailabilityPrice implements Serializable {

    private Long id;

    private Accommodation accommodation;

    private AccommodationChange accommodationChange;

    private double amount;

    private LocalDateTime dateFrom;

    private LocalDateTime dateUntil;

    public AvailabilityPrice() {
    }

    public AvailabilityPrice(Accommodation accommodation, AccommodationChange accommodationChange, double amount, LocalDateTime dateFrom, LocalDateTime dateUntil) {
        this.accommodation = accommodation;
        this.accommodationChange = accommodationChange;
        this.amount = amount;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }

    public AvailabilityPrice(Long id, Accommodation accommodation, AccommodationChange accommodationChange, double amount, LocalDateTime dateFrom, LocalDateTime dateUntil) {
        this.id = id;
        this.accommodation = accommodation;
        this.accommodationChange = accommodationChange;
        this.amount = amount;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public AccommodationChange getAccommodationChange() {
        return accommodationChange;
    }

    public void setAccommodationChange(AccommodationChange accommodationChange) {
        this.accommodationChange = accommodationChange;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDateTime dateUntil) {
        this.dateUntil = dateUntil;
    }
}
