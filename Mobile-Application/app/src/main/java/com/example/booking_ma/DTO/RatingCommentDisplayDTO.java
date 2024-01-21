package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.RatingCommentType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RatingCommentDisplayDTO {
    private Long id;
    private Long guestsId;
    private String guest;
    private RatingCommentType type;
    private Long ownersId;
    private Long accommodationsId;
    private float rating;
    private String comment;
    private LocalDateTime time;

    public RatingCommentDisplayDTO() {
    }

    public RatingCommentDisplayDTO(Long id, Long guestsId, String guest, RatingCommentType type, Long ownersId, Long accommodationsId, int rating, String comment, LocalDateTime time) {
        this.id = id;
        this.guestsId = guestsId;
        this.guest = guest;
        this.type = type;
        this.ownersId = ownersId;
        this.accommodationsId = accommodationsId;
        this.rating = rating;
        this.comment = comment;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestsId() {
        return guestsId;
    }

    public void setGuestsId(Long guestsId) {
        this.guestsId = guestsId;
    }

    public RatingCommentType getType() {
        return type;
    }

    public void setType(RatingCommentType type) {
        this.type = type;
    }

    public Long getOwnersId() {
        return ownersId;
    }

    public void setOwnersId(Long ownersId) {
        this.ownersId = ownersId;
    }

    public Long getAccommodationsId() {
        return accommodationsId;
    }

    public void setAccommodationsId(Long accommodationsId) {
        this.accommodationsId = accommodationsId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return time.format(formatter);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }
}
