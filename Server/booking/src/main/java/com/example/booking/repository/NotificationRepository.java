package com.example.booking.repository;

import com.example.booking.model.AvailabilityPrice;
import com.example.booking.model.Notification;
import com.example.booking.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n " +
            "WHERE n.receiverId = :receiverId AND n.received = false " +
            "ORDER BY n.timestamp DESC")
    List<Notification> findUnreceivedByReceiverId(@Param("receiverId") Long receiverId);

    List<Notification> findAllByReceiverId(Long receiverId);
}
