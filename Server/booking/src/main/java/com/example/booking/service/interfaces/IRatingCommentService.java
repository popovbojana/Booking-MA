package com.example.booking.service.interfaces;

import com.example.booking.dto.AllRatingsDisplay;
import com.example.booking.dto.ApprovalDTO;
import com.example.booking.dto.RateCommentDTO;
import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.model.RatingComment;

import java.util.List;

public interface IRatingCommentService {
    void rateComment(RateCommentDTO rateCommentDTO) throws NoDataWithId;
    void deleteRatingComment(Long id) throws NoDataWithId;

    List<RatingCommentDisplayDTO> getAllForGuest(Long id);

    AllRatingsDisplay getAllForOwner(Long id) throws NoDataWithId;

    AllRatingsDisplay getAllForAccommodation(Long id) throws NoDataWithId;

    void report(Long id) throws NoDataWithId;
    float calculateAverageRating(List<RatingComment> allRatings);

    List<RatingCommentDisplayDTO> getAllUnapproved() throws NoDataWithId;

    void approve(Long id, boolean approval) throws NoDataWithId;
}
