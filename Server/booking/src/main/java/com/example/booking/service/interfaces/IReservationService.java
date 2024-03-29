package com.example.booking.service.interfaces;

import com.example.booking.dto.*;
import com.example.booking.exceptions.AlreadyChangedState;
import com.example.booking.exceptions.DeadlineException;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.RequirementNotSatisfied;
import com.example.booking.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    Optional<Reservation> getReservation(Long id);

    List<Reservation> GetAll();

    void createReservation(ReservationDTO reservationRequest) throws NoDataWithId;

    void denyReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState;

    void approveReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState;

    void cancelReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState, DeadlineException;

    void deleteReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState;

    List<ReservationDisplayDTO> getGuestReservations(Long guestId) throws NoDataWithId;

    List<ReservationDisplayDTO> getGuestReservationsBySearch(Long guestId, SearchDTO searchRequest) throws NoDataWithId;

    List<ReservationDisplayDTO> getGuestPendingReservations(Long guestId) throws NoDataWithId;

    List<ReservationDisplayDTO> getOwnerReservations(Long ownerId) throws NoDataWithId;

    List<ReservationDisplayDTO> getOwnerReservationsBySearch(Long ownerId, SearchDTO searchRequest) throws NoDataWithId;

    List<ReservationDisplayDTO> getOwnerPendingReservations(Long ownerId) throws NoDataWithId;

    boolean checkAccommodationInDateInterval(CheckAccommodationAvailabilityDTO checkAccommodationAvailabilityRequest) throws NoDataWithId, RequirementNotSatisfied;

    ReportAccommodationDTO getReportForAccommodation(Long id) throws NoDataWithId;

    ReportAllAccommodationsDTO getReportForAllAccommodations(Long id, ReportRangeDTO range) throws NoDataWithId;
}
