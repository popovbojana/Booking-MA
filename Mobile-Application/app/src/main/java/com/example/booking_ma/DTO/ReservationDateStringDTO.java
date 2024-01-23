package com.example.booking_ma.DTO;

import java.time.LocalDateTime;

public class ReservationDateStringDTO {

    private Long guestId;
    private Long ownerId;
    private Long accommodationId;
    private String checkIn;
    private String checkOut;
    private int guestsNumber;
    private float totalCost;

    public ReservationDateStringDTO(Long guestId, Long ownerId, Long accommodationId, String checkIn, String checkOut, int guestsNumber, float totalCost) {
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.accommodationId = accommodationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsNumber = guestsNumber;
        this.totalCost = totalCost;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getGuestsNumber() {
        return guestsNumber;
    }

    public void setGuestsNumber(int guestsNumber) {
        this.guestsNumber = guestsNumber;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
