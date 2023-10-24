package com.example.booking.repository;

import com.example.booking.model.AccommodationChange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationChangeRepository extends CrudRepository<AccommodationChange, Long> {

    List<AccommodationChange> findAll();
}
