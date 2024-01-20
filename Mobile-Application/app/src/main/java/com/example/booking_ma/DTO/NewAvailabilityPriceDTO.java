package com.example.booking_ma.DTO;

import java.time.LocalDateTime;

public class NewAvailabilityPriceDTO {

    private double amount;
//    private LocalDateTime dateFrom;
    private String dateFrom;
//    private LocalDateTime dateUntil;
    private String dateUntil;

    public NewAvailabilityPriceDTO() {
    }

    public NewAvailabilityPriceDTO(double amount, String dateFrom, String dateUntil) {
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

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(String dateUntil) {
        this.dateUntil = dateUntil;
    }
}