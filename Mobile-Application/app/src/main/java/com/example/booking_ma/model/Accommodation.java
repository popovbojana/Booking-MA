package com.example.booking_ma.model;

import java.time.LocalDate;
import java.util.List;

public class Accommodation {

    private Long id;
    private String name;
    private int imageResource;
    private String description;
    private List<Comment> comments;
    private String address;
    private String latitude;
    private String longitude;
    private List<LocalDate> reservedDates;
    private List<LocalDate> freeDates;
    private List<String> amentites;
    private float price;
    private float stars;

    public Accommodation() {
    }

    public Accommodation(String name, int imageResource, String description, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reservedDates = reservedDates;
        this.freeDates = freeDates;
        this.amentites = amentites;
        this.price = price;
        this.stars = stars;
    }

    public Accommodation(String name, int imageResource, String description, List<Comment> comments, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.comments = comments;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reservedDates = reservedDates;
        this.freeDates = freeDates;
        this.amentites = amentites;
        this.price = price;
        this.stars = stars;
    }

    public Accommodation(Long id, String name, int imageResource, String description, List<Comment> comments, String address, String latitude, String longitude, List<LocalDate> reservedDates, List<LocalDate> freeDates, List<String> amentites, float price, float stars) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.comments = comments;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reservedDates = reservedDates;
        this.freeDates = freeDates;
        this.amentites = amentites;
        this.price = price;
        this.stars = stars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<LocalDate> getReservedDates() {
        return reservedDates;
    }

    public void setReservedDates(List<LocalDate> reservedDates) {
        this.reservedDates = reservedDates;
    }

    public List<LocalDate> getFreeDates() {
        return freeDates;
    }

    public void setFreeDates(List<LocalDate> freeDates) {
        this.freeDates = freeDates;
    }

    public List<String> getAmentites() {
        return amentites;
    }

    public void setAmentites(List<String> amentites) {
        this.amentites = amentites;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
