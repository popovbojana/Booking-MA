package com.example.booking.repository;

import com.example.booking.model.*;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    public Optional<Reservation> findById(Long id);

    public List<Reservation> findAll();

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId")
    List<Reservation> findAllReservationsByGuestId(@Param("guestId") Long guestId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (r.checkIn BETWEEN :dateFrom AND :dateUntil AND r.checkOut BETWEEN :dateFrom AND :dateUntil)" +
            " AND r.guest.id = :guestId AND r.accommodation.name = :accommodationName")
    List<Reservation> findAllReservationsByGuestIdAndAccommodationNameAndBetweenDates(
            @Param("guestId") Long guestId,
            @Param("accommodationName") String accommodationName,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateUntil") LocalDateTime dateUntil);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId AND r.reservationState = 'PENDING'")
    List<Reservation> findAllPendingByGuestId(@Param("guestId") Long guestId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId AND r.reservationState = 'APPROVED'")
    List<Reservation> findAllApprovedByGuestId(@Param("guestId") Long guestId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId AND r.reservationState = 'DENIED'")
    List<Reservation> findAllDeniedByGuestId(@Param("guestId") Long guestId);

    @Query("SELECT r FROM Reservation r WHERE r.owner.id = :ownerId")
    List<Reservation> findAllByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Reservation r WHERE r.owner.id = :ownerId AND r.reservationState = 'APPROVED'")
    List<Reservation> findAllApprovedReservationsByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (r.checkIn BETWEEN :dateFrom AND :dateUntil AND r.checkOut BETWEEN :dateFrom AND :dateUntil)" +
            " AND r.owner.id = :ownerId AND r.accommodation.name = :accommodationName")
    List<Reservation> findAllReservationsByOwnerIdAndAccommodationNameAndBetweenDates(
            @Param("ownerId") Long ownerId,
            @Param("accommodationName") String accommodationName,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateUntil") LocalDateTime dateUntil);

    @Query("SELECT r FROM Reservation r WHERE r.owner.id = :ownerId AND r.reservationState = 'PENDING'")
    List<Reservation> findAllPendingByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Reservation r WHERE r.owner.id = :ownerId AND r.reservationState = 'APPROVED'")
    List<Reservation> findAllApprovedByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Reservation r WHERE r.owner.id = :ownerId AND r.reservationState = 'DENIED'")
    List<Reservation> findAllDeniedByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (:checkIn BETWEEN r.checkIn AND r.checkOut OR :checkOut BETWEEN r.checkIn AND r.checkOut)" +
            "AND r.reservationState = 'APPROVED'")
    List<Reservation> findApprovedReservationsBetweenDates(
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (:checkIn BETWEEN r.checkIn AND r.checkOut OR :checkOut BETWEEN r.checkIn AND r.checkOut)" +
            "AND r.reservationState = 'PENDING'")
    List<Reservation> findPendingReservationsBetweenDates(
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut);

    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :id AND r.reservationState = 'APPROVED'")
    List<Reservation> findAllReservationsForAccommodation(Long id);
}
