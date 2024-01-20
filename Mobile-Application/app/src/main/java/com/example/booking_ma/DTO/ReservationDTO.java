package com.example.booking_ma.DTO;

import java.time.LocalDateTime;

public class ReservationDTO {

    private Long guestId;
    private Long ownerId;
    private Long accommodationId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int guestsNumber;
    private float totalCost;

    public ReservationDTO(Long guestId, Long ownerId, Long accommodationId, LocalDateTime checkIn, LocalDateTime checkOut, int guestsNumber, float totalCost) {
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

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
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
