package com.example.booking.service.interfaces;

import com.example.booking.dto.AllRatingsDisplay;
import com.example.booking.dto.ApprovalDTO;
import com.example.booking.dto.RateCommentDTO;
import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.RequirementNotSatisfied;
import com.example.booking.model.RatingComment;

import java.util.List;

public interface IRatingCommentService {
    void rateComment(RateCommentDTO rateCommentDTO) throws NoDataWithId, RequirementNotSatisfied;
    void deleteRatingComment(Long id) throws NoDataWithId;

    List<RatingCommentDisplayDTO> getAllForGuest(Long id);

    AllRatingsDisplay getAllForOwner(Long id) throws NoDataWithId;

    AllRatingsDisplay getAllForAccommodation(Long id) throws NoDataWithId;

    void report(Long id) throws NoDataWithId;

    List<RatingCommentDisplayDTO> getReportedOwnersComments() throws NoDataWithId;

    List<RatingCommentDisplayDTO> getReportedComments() throws NoDataWithId;

    boolean handleReportedComment(Long ratingCommentId, ApprovalDTO approval) throws NoDataWithId, RequirementNotSatisfied;

    float calculateAverageRating(List<RatingComment> allRatings);

    List<RatingCommentDisplayDTO> getAllUnapproved() throws NoDataWithId;

    void approve(Long id, boolean approval) throws NoDataWithId;
}
