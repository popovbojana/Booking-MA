package com.example.booking.controller;

import com.example.booking.dto.CheckAccommodationAvailabilityDTO;
import com.example.booking.dto.MessageDTO;
import com.example.booking.dto.ReservationDTO;
import com.example.booking.dto.SearchDTO;
import com.example.booking.service.interfaces.IAccommodationChangeService;
import com.example.booking.service.interfaces.IAccommodationService;
import com.example.booking.service.interfaces.IReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations/")
public class ReservationController {

    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService){
        this.reservationService = reservationService;
    }


    @PostMapping(value = "reservation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservation) {
        try{
            this.reservationService.createReservation(reservation);
            return new ResponseEntity<>(new MessageDTO("Successfully created reservation"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "reservation-deny/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> denyReservation(@PathVariable("accommodationId") Long accommodationId) {
        try{
            this.reservationService.denyReservation(accommodationId);
            return new ResponseEntity<>(new MessageDTO("Successfully denied reservation"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "reservation-approve/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> approveReservation(@PathVariable("accommodationId") Long accommodationId) {
        try{
            this.reservationService.approveReservation(accommodationId);
            return new ResponseEntity<>(new MessageDTO("Successfully approved reservation"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "reservation-cancel/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> cancelReservation(@PathVariable("accommodationId") Long accommodationId) {
        try{
            this.reservationService.cancelReservation(accommodationId);
            return new ResponseEntity<>(new MessageDTO("Successfully canceled reservation"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "reservation-delete/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        try{
            this.reservationService.deleteReservation(reservationId);
            return new ResponseEntity<>(new MessageDTO("Successfully deleted reservation"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "guest-reservations/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getGuestReservations(@PathVariable("guestId") Long guestId) {
        try{
            return new ResponseEntity<>(this.reservationService.getGuestReservations(guestId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "guest-reservations-search/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getGuestReservationsBySearch(@PathVariable("guestId") Long guestId, @RequestBody SearchDTO search) {
        try{
            return new ResponseEntity<>(this.reservationService.getGuestReservationsBySearch(guestId, search), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "guest-pending-reservations/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<?> getGuestPendingReservations(@PathVariable("guestId") Long guestId) {
        try{
            return new ResponseEntity<>(this.reservationService.getGuestPendingReservations(guestId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "guest-approved-reservations/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<?> getGuestApprovedReservations(@PathVariable("guestId") Long guestId) {
        try{
            return new ResponseEntity<>(this.reservationService.getGuestApprovedReservations(guestId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "guest-denied-reservations/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Guest')")
    public ResponseEntity<?> getGuestDeniedReservations(@PathVariable("guestId") Long guestId) {
        try{
            return new ResponseEntity<>(this.reservationService.getGuestDeniedReservations(guestId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "owner-reservations/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getOwnerReservations(@PathVariable("ownerId") Long ownerId) {
        try{
            return new ResponseEntity<>(this.reservationService.getOwnerReservations(ownerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "owner-reservations-search/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getOwnerReservationsBySearch(@PathVariable("ownerId") Long ownerId, @RequestBody SearchDTO search) {
        try{
            return new ResponseEntity<>(this.reservationService.getOwnerReservationsBySearch(ownerId, search), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "owner-pending-reservations/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getOwnerPendingReservations(@PathVariable("ownerId") Long ownerId) {
        try{
            return new ResponseEntity<>(this.reservationService.getOwnerPendingReservations(ownerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "owner-approved-reservations/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getOwnerApprovedReservations(@PathVariable("ownerId") Long ownerId) {
        try{
            return new ResponseEntity<>(this.reservationService.getOwnerApprovedReservations(ownerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "owner-denied-reservations/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> getOwnerDeniedReservations(@PathVariable("ownerId") Long ownerId) {
        try{
            return new ResponseEntity<>(this.reservationService.getOwnerDeniedReservations(ownerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "check-in-date-interval", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<?> checkAccommodationInDateInterval(@RequestBody CheckAccommodationAvailabilityDTO checkAccommodationAvailabilityRequest) {
        try{
            return new ResponseEntity<>(this.reservationService.checkAccommodationInDateInterval(checkAccommodationAvailabilityRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
