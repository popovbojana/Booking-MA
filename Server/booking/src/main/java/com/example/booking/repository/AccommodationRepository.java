package com.example.booking.repository;

import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {

    @Query("select a from Accommodation a where a.approved = false")
    List<Accommodation> findAllNewAccommodation();

    @Query("select a from Accommodation a where a.owner.id = :ownersId")
    List<Accommodation> findAllAccommodationsForOwner(Long ownersId);

    @Query("select a from Accommodation a where a.hasChanges = true ")
    List<Accommodation> findAllAccommodationsWithChange();

    List<Accommodation> findByAccommodationChange(AccommodationChange accommodationChange);
    List<Accommodation> findAll();
}
