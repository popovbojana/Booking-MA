package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.PriceType;

import java.util.List;

public class AccommodationDisplayDTO {

    private Long id;
    private Long ownersId;
    private String name;
    private String description;
    private String amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private PriceType priceType;
    private List<AvailabilityDisplayDTO> availabilities;
    private int cancellationDeadlineInDays;
    private boolean approved;
    private boolean hasChanges;
    private String address;
    private double latitude;
    private double longitude;
    private double finalRating;

    public AccommodationDisplayDTO() {
    }

    public AccommodationDisplayDTO(Long id, Long ownersId, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityDisplayDTO> availabilities, int cancellationDeadlineInDays, boolean approved, boolean hasChanges, String address, double latitude, double longitude, double finalRating) {
        this.id = id;
        this.ownersId = ownersId;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.approved = approved;
        this.hasChanges = hasChanges;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalRating = finalRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnersId() {
        return ownersId;
    }

    public void setOwnersId(Long ownersId) {
        this.ownersId = ownersId;
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

    public List<AvailabilityDisplayDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDisplayDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public int getCancellationDeadlineInDays() {
        return cancellationDeadlineInDays;
    }

    public void setCancellationDeadlineInDays(int cancellationDeadlineInDays) {
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isHasChanges() {
        return hasChanges;
    }

    public void setHasChanges(boolean hasChanges) {
        this.hasChanges = hasChanges;
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
}
