package com.example.booking_ma.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTimeFrom = dateFrom.format(formatter);
        String formattedDateTimeUntil = dateUntil.format(formatter);
        return formattedDateTimeFrom + " - " + formattedDateTimeUntil + ", price: " + amount + "\n";
    }
}
