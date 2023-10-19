package com.example.booking.repository;

import com.example.booking.model.Accommodation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
}
