package com.example.booking_ma.DTO;

public class FavoriteAccommodationDTO {
    private Long guestsId;
    private Long accommodationsId;

    public FavoriteAccommodationDTO() {
    }

    public FavoriteAccommodationDTO(Long guestsId, Long accommodationsId) {
        this.guestsId = guestsId;
        this.accommodationsId = accommodationsId;
    }

    public Long getGuestsId() {
        return guestsId;
    }

    public void setGuestsId(Long guestsId) {
        this.guestsId = guestsId;
    }

    public Long getAccommodationsId() {
        return accommodationsId;
    }

    public void setAccommodationsId(Long accommodationsId) {
        this.accommodationsId = accommodationsId;
    }
}
