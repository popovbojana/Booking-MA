package com.example.booking.repository;

import com.example.booking.model.AvailabilityPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityPriceRepository extends CrudRepository<AvailabilityPrice, Long> {

    @Query("SELECT ap FROM AvailabilityPrice ap " +
            "WHERE (:checkIn BETWEEN ap.dateFrom AND ap.dateUntil AND :checkOut BETWEEN ap.dateFrom AND ap.dateUntil) " +
            "AND ap.accommodation.id = :accommodationId")
    List<AvailabilityPrice> findAvailabilityPricesByDateRangeAndAccommodationId(
            @Param("accommodationId") Long accommodationId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut);

    @Query("SELECT ap FROM AvailabilityPrice ap " +
            "WHERE (ap.accommodation.id = :accommodationId)")
    List<AvailabilityPrice> findAvailabilityPricesByAccommodationId(
            @Param("accommodationId") Long accommodationId);
}
