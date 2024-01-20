package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.PriceType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccommodationChangesDTO {

    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private int cancellationDeadlineInDays;
    private double standardPrice;
    private String dateFrom;
    private String dateUntil;
    private double amount;


    public AccommodationChangesDTO() {
    }

    public AccommodationChangesDTO(String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType myPriceType, int cancellationDeadlineInDays, double standardPrice) {
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        if (myPriceType != null){
            this.priceType = myPriceType;
        }
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.standardPrice = standardPrice;
    }

    public AccommodationChangesDTO(String dateFrom, String dateUntil, double amount) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.amount = amount;

        this.name = "";
        this.description = "";
        this.amenities = "";
        this.minGuests = -1;
        this.maxGuests = -1;
        this.type = "";
        this.priceType = null;
        this.standardPrice = -1;
        this.cancellationDeadlineInDays = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public int getCancellationDeadlineInDays() {
        return cancellationDeadlineInDays;
    }

    public void setCancellationDeadlineInDays(int cancellationDeadlineInDays) {
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
