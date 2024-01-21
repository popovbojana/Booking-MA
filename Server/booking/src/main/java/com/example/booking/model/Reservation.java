package com.example.booking.model;

import com.example.booking.dto.ReservationDisplayDTO;
import com.example.booking.dto.UserDisplayDTO;
import com.example.booking.model.enums.ReservationState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private int guestsNumber;

    private float totalCost;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    private LocalDateTime cancelationDeadline;

    private int guestCancelationsNumber;

    public Reservation(Guest guest, Owner owner, Accommodation accommodation, LocalDateTime checkIn, LocalDateTime checkOut, int guestsNumber, float totalCost, ReservationState reservationState, LocalDateTime cancelationDeadline, int guestCancelationsNumber) {
        this.guest = guest;
        this.owner = owner;
        this.accommodation = accommodation;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsNumber = guestsNumber;
        this.totalCost = totalCost;
        this.reservationState = reservationState;
        this.cancelationDeadline = cancelationDeadline;
        this.guestCancelationsNumber = guestCancelationsNumber;
    }

    public ReservationDisplayDTO parseToDisplay() {
        return new ReservationDisplayDTO(id, guest.getId(), owner.getId(), accommodation.getId(), checkIn, checkOut, guestsNumber, totalCost, reservationState, cancelationDeadline, guestCancelationsNumber);
    }
}
