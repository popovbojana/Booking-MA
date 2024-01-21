package com.example.booking_ma.DTO;

import java.util.List;

public class AllRatingsDisplay {
    private double averageRating;
    private List<RatingCommentDisplayDTO> allRatingComments;

    public AllRatingsDisplay() {
    }

    public AllRatingsDisplay(double averageRating, List<RatingCommentDisplayDTO> allRatingComments) {
        this.averageRating = averageRating;
        this.allRatingComments = allRatingComments;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<RatingCommentDisplayDTO> getAllRatingComments() {
        return allRatingComments;
    }

    public void setAllRatingComments(List<RatingCommentDisplayDTO> allRatingComments) {
        this.allRatingComments = allRatingComments;
    }
}