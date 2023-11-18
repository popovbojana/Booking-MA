package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.RatingCommentType;

import java.time.LocalDateTime;

public class RatingCommentDisplayDTO {
    private Long guestsId;
    private RatingCommentType type;
    private Long ownersId;
    private Long accommodationsId;
    private int rating;
    private String comment;
    private LocalDateTime time;

    public RatingCommentDisplayDTO() {
    }

    public RatingCommentDisplayDTO(Long guestsId, RatingCommentType type, Long ownersId, Long accommodationsId, int rating, String comment, LocalDateTime time) {
        this.guestsId = guestsId;
        this.type = type;
        this.ownersId = ownersId;
        this.accommodationsId = accommodationsId;
        this.rating = rating;
        this.comment = comment;
        this.time = time;
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
}
