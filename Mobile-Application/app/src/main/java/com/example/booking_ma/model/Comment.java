package com.example.booking_ma.model;

import java.io.Serializable;

public class Comment implements Serializable {

    private Long id;
    private Accommodation accommodation;
    private Guest user;
    private String message;
    private float stars;

    public Comment(){}

    public Comment(Accommodation accommodation, Guest user, String message, float stars) {
        this.accommodation = accommodation;
        this.user = user;
        this.message = message;
        this.stars = stars;
    }

    public Comment(Long id, Accommodation accommodation, Guest user, String message, float stars) {
        this.id = id;
        this.accommodation = accommodation;
        this.user = user;
        this.message = message;
        this.stars = stars;
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

    public Guest getUser() {
        return user;
    }

    public void setUser(Guest user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
