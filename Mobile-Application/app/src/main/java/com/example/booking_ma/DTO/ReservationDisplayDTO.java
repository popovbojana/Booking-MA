package com.example.booking_ma.DTO;

import com.example.booking_ma.model.enums.ReservationState;

import java.time.LocalDateTime;

public class ReservationDisplayDTO {

    private Long id;
    private Long guestId;
    private Long ownerId;
    private Long accommodationId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int guestsNumber;
    private float totalCost;
    private ReservationState reservationState;
    private LocalDateTime cancelationDeadline;
    private int guestCancelationsNumber;

    public ReservationDisplayDTO(Long id, Long guestId, Long ownerId, Long accommodationId, LocalDateTime checkIn, LocalDateTime checkOut, int guestsNumber, float totalCost, ReservationState reservationState, LocalDateTime cancelationDeadline, int guestCancelationsNumber) {
        this.id = id;
        this.guestId = guestId;
        this.ownerId = ownerId;
        this.accommodationId = accommodationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsNumber = guestsNumber;
        this.totalCost = totalCost;
        this.reservationState = reservationState;
        this.cancelationDeadline = cancelationDeadline;
        this.guestCancelationsNumber = guestCancelationsNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ReservationState getReservationState() {
        return reservationState;
    }

    public void setReservationState(ReservationState reservationState) {
        this.reservationState = reservationState;
    }

    public LocalDateTime getCancelationDeadline() {
        return cancelationDeadline;
    }

    public void setCancelationDeadline(LocalDateTime cancelationDeadline) {
        this.cancelationDeadline = cancelationDeadline;
    }

    public int getGuestCancelationsNumber() {
        return guestCancelationsNumber;
    }

    public void setGuestCancelationsNumber(int guestCancelationsNumber) {
        this.guestCancelationsNumber = guestCancelationsNumber;
    }
}
