package com.example.booking_ma.DTO;

import java.time.LocalDateTime;

public class AvailabilityDisplayDTO {

    private double amount;
    private LocalDateTime dateFrom;
    private LocalDateTime dateUntil;

    public AvailabilityDisplayDTO() {
    }

    public AvailabilityDisplayDTO(double amount, LocalDateTime dateFrom, LocalDateTime dateUntil) {
        this.amount = amount;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
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
