package com.example.booking.repository;

import com.example.booking.model.AvailabilityPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityPriceRepository extends CrudRepository<AvailabilityPrice, Long> {
}
