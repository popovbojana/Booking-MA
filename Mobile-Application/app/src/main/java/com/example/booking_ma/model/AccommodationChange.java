package com.example.booking_ma.model;

import com.example.booking_ma.model.enums.PriceType;

import java.io.Serializable;
import java.util.List;

public class AccommodationChange implements Serializable {

    private Long id;

    private Accommodation accommodation;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    private PriceType priceType;

    private List<AvailabilityPrice> availabilities;

    private int cancellationDeadlineInDays;

    public AccommodationChange() {
    }

    public AccommodationChange(Accommodation accommodation, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays) {
        this.accommodation = accommodation;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }

    public AccommodationChange(Long id, Accommodation accommodation, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays) {
        this.id = id;
        this.accommodation = accommodation;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
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

    public List<AvailabilityPrice> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityPrice> availabilities) {
        this.availabilities = availabilities;
    }

    public int getCancellationDeadlineInDays() {
        return cancellationDeadlineInDays;
    }

    public void setCancellationDeadlineInDays(int cancellationDeadlineInDays) {
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }
}
