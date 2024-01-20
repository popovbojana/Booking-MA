package com.example.booking.repository;

import com.example.booking.model.Accommodation;
import com.example.booking.model.RatingComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingCommentRepository extends CrudRepository<RatingComment, Long> {
    @Query("select rc from RatingComment rc where rc.owner.id = :id")
    List<RatingComment> findAllForOwner(Long id);

    @Query("select rc from RatingComment rc where rc.accommodation.id = :id and rc.approved = true")
    List<RatingComment> findAllForAccommodation(Long id);

    @Query("select rc from RatingComment rc where rc.approved = false")
    List<RatingComment> getAllUnapproved();
}
