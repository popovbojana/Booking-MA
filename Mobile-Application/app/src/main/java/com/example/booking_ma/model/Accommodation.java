package com.example.booking_ma.model;

import com.example.booking_ma.model.enums.PriceType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Accommodation implements Serializable {

    private Long id;

    private Owner owner;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    private PriceType priceType;

    private List<AvailabilityPrice> availabilities;

    private int cancellationDeadlineInDays;

    private boolean approved;

    private boolean hasChanges;

    private AccommodationChange accommodationChange;

    private List<RatingComment> ratingComments;

    private String address;

    private double latitude;

    private double longitude;

    private double finalRating;

    public Accommodation() {
    }

    public Accommodation(Owner owner, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays, boolean approved, boolean hasChanges, AccommodationChange accommodationChange, List<RatingComment> ratingComments, String address, double latitude, double longitude, double finalRating) {
        this.owner = owner;
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
        this.accommodationChange = accommodationChange;
        this.ratingComments = ratingComments;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalRating = finalRating;
    }

    public Accommodation(Long id, Owner owner, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays, boolean approved, boolean hasChanges, AccommodationChange accommodationChange, List<RatingComment> ratingComments, String address, double latitude, double longitude, double finalRating) {
        this.id = id;
        this.owner = owner;
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
        this.accommodationChange = accommodationChange;
        this.ratingComments = ratingComments;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public AccommodationChange getAccommodationChange() {
        return accommodationChange;
    }

    public void setAccommodationChange(AccommodationChange accommodationChange) {
        this.accommodationChange = accommodationChange;
    }

    public List<RatingComment> getRatingComments() {
        return ratingComments;
    }

    public void setRatingComments(List<RatingComment> ratingComments) {
        this.ratingComments = ratingComments;
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

    //
//    private Long id;
//    private String name;
//    private int imageResource;
//    private String description;
//    private List<Comment> comments;
//    private String address;
//    private String latitude;
//    private String longitude;
//    private List<LocalDate> reservedDates;
//    private List<LocalDate> freeDates;
//    private List<String> amentites;
//    private float price;
//    private float stars;
//
//    public Accommodation() {
//    }
//
//    public Accommodation(String name, int imageResource, String description, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
//        this.name = name;
//        this.imageResource = imageResource;
//        this.description = description;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.reservedDates = reservedDates;
//        this.freeDates = freeDates;
//        this.amentites = amentites;
//        this.price = price;
//        this.stars = stars;
//    }
//
//    public Accommodation(String name, int imageResource, String description, List<Comment> comments, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
//        this.name = name;
//        this.imageResource = imageResource;
//        this.description = description;
//        this.comments = comments;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.reservedDates = reservedDates;
//        this.freeDates = freeDates;
//        this.amentites = amentites;
//        this.price = price;
//        this.stars = stars;
//    }
//
//    public Accommodation(Long id, String name, int imageResource, String description, List<Comment> comments, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
//        this.id = id;
//        this.name = name;
//        this.imageResource = imageResource;
//        this.description = description;
//        this.comments = comments;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.reservedDates = reservedDates;
//        this.freeDates = freeDates;
//        this.amentites = amentites;
//        this.price = price;
//        this.stars = stars;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getImageResource() {
//        return imageResource;
//    }
//
//    public void setImageResource(int imageResource) {
//        this.imageResource = imageResource;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
//
//    public List<LocalDate> getReservedDates() {
//        return reservedDates;
//    }
//
//    public void setReservedDates(List<LocalDate> reservedDates) {
//        this.reservedDates = reservedDates;
//    }
//
//    public List<LocalDate> getFreeDates() {
//        return freeDates;
//    }
//
//    public void setFreeDates(List<LocalDate> freeDates) {
//        this.freeDates = freeDates;
//    }
//
//    public List<String> getAmentites() {
//        return amentites;
//    }
//
//    public void setAmentites(List<String> amentites) {
//        this.amentites = amentites;
//    }
//
//    public float getPrice() {
//        return price;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//
//    public float getStars() {
//        return stars;
//    }
//
//    public void setStars(float stars) {
//        this.stars = stars;
//    }
}
