package com.example.booking_ma.DTO;

public class RateCommentDTO {
    private Long guestsId;
    private String type;
    private Long ownersId;
    private Long accommodationsId;
    private float rating;
    private String comment;

    public RateCommentDTO() {
    }

    public RateCommentDTO(Long guestsId, String type, Long ownersId, Long accommodationsId, float rating, String comment) {
        this.guestsId = guestsId;
        this.type = type;
        this.ownersId = ownersId;
        this.accommodationsId = accommodationsId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getGuestsId() {
        return guestsId;
    }

    public void setGuestsId(Long guestsId) {
        this.guestsId = guestsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}