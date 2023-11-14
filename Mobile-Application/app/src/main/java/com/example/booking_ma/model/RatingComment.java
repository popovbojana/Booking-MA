package com.example.booking_ma.model;

import com.example.booking_ma.model.enums.RatingCommentType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RatingComment implements Serializable {

    private Long id;

    private RatingCommentType type;

    private Guest guest;

    private Owner owner;

    private Accommodation accommodation;

    private int rating;

    private String comment;

    private LocalDateTime time;

    private boolean approved;

    private boolean reported;

    public RatingComment() {
    }

    public RatingComment(RatingCommentType type, Guest guest, Owner owner, Accommodation accommodation, int rating, String comment, LocalDateTime time, boolean approved, boolean reported) {
        this.type = type;
        this.guest = guest;
        this.owner = owner;
        this.accommodation = accommodation;
        this.rating = rating;
        this.comment = comment;
        this.time = time;
        this.approved = approved;
        this.reported = reported;
    }

    public RatingComment(Long id, RatingCommentType type, Guest guest, Owner owner, Accommodation accommodation, int rating, String comment, LocalDateTime time, boolean approved, boolean reported) {
        this.id = id;
        this.type = type;
        this.guest = guest;
        this.owner = owner;
        this.accommodation = accommodation;
        this.rating = rating;
        this.comment = comment;
        this.time = time;
        this.approved = approved;
        this.reported = reported;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingCommentType getType() {
        return type;
    }

    public void setType(RatingCommentType type) {
        this.type = type;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
