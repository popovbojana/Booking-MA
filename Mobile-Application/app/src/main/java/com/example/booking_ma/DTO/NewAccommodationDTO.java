package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.PriceType;

import java.util.List;

public class NewAccommodationDTO {

    private String name;
    private String description;
    private String location;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private List<NewAvailabilityPriceDTO> availability;
    private int cancellationDeadlineInDays;
    private String address;
    private double latitude;
    private double longitude;
    private double finalRating;
    private double standardPrice;

    public NewAccommodationDTO() {
    }

    public NewAccommodationDTO(String name, String description, String location, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<NewAvailabilityPriceDTO> availability, int cancellationDeadlineInDays, String address, double latitude, double longitude, double finalRating, double standardPrice) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availability = availability;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalRating = finalRating;
        this.standardPrice = standardPrice;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<NewAvailabilityPriceDTO> getAvailability() {
        return availability;
    }

    public void setAvailability(List<NewAvailabilityPriceDTO> availability) {
        this.availability = availability;
    }

    public int getCancellationDeadlineInDays() {
        return cancellationDeadlineInDays;
    }

    public void setCancellationDeadlineInDays(int cancellationDeadlineInDays) {
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(double finalRating) {
        this.finalRating = finalRating;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
    }
}
