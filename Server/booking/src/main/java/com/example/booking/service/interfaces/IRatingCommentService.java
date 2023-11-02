package com.example.booking.service.interfaces;

import com.example.booking.dto.AllRatingsDisplay;
import com.example.booking.dto.RateCommentDTO;
import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.exceptions.NoDataWithId;

import java.util.List;

public interface IRatingCommentService {
    void rateComment(RateCommentDTO rateCommentDTO) throws NoDataWithId;
    void deleteRatingComment(Long id) throws NoDataWithId;

    List<RatingCommentDisplayDTO> getAllForGuest(Long id);

    AllRatingsDisplay getAllForOwner(Long id) throws NoDataWithId;

    AllRatingsDisplay getAllForAccommodation(Long id) throws NoDataWithId;
}
