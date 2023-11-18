package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.PriceType;

import java.util.List;

public class AccommodationChangeDisplayDTO {
    private Long accommodationId;
    private String newName;
    private String newDescription;
    private String newAmenities;
    private int newMinGuests;
    private int newMaxGuests;
    private String newType;
    private PriceType newPriceType;
    private List<AvailabilityDisplayDTO> availabilities;
    private int newCancellationDeadlineInDays;
    private double newStandardPrice;

    public AccommodationChangeDisplayDTO() {
    }

    public AccommodationChangeDisplayDTO(Long accommodationId, String newName, String newDescription, String newAmenities, int newMinGuests, int newMaxGuests, String newType, PriceType newPriceType, List<AvailabilityDisplayDTO> availabilities, int newCancellationDeadlineInDays, double newStandardPrice) {
        this.accommodationId = accommodationId;
        this.newName = newName;
        this.newDescription = newDescription;
        this.newAmenities = newAmenities;
        this.newMinGuests = newMinGuests;
        this.newMaxGuests = newMaxGuests;
        this.newType = newType;
        this.newPriceType = newPriceType;
        this.availabilities = availabilities;
        this.newCancellationDeadlineInDays = newCancellationDeadlineInDays;
        this.newStandardPrice = newStandardPrice;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getNewAmenities() {
        return newAmenities;
    }

    public void setNewAmenities(String newAmenities) {
        this.newAmenities = newAmenities;
    }

    public int getNewMinGuests() {
        return newMinGuests;
    }

    public void setNewMinGuests(int newMinGuests) {
        this.newMinGuests = newMinGuests;
    }

    public int getNewMaxGuests() {
        return newMaxGuests;
    }

    public void setNewMaxGuests(int newMaxGuests) {
        this.newMaxGuests = newMaxGuests;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public PriceType getNewPriceType() {
        return newPriceType;
    }

    public void setNewPriceType(PriceType newPriceType) {
        this.newPriceType = newPriceType;
    }

    public List<AvailabilityDisplayDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDisplayDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public int getNewCancellationDeadlineInDays() {
        return newCancellationDeadlineInDays;
    }

    public void setNewCancellationDeadlineInDays(int newCancellationDeadlineInDays) {
        this.newCancellationDeadlineInDays = newCancellationDeadlineInDays;
    }

    public double getNewStandardPrice() {
        return newStandardPrice;
    }

    public void setNewStandardPrice(double newStandardPrice) {
        this.newStandardPrice = newStandardPrice;
    }
}

