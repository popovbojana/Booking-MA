package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllRatingsDisplay {
    private double averageRating;
    private List<RatingCommentDisplayDTO> allRatingComments;
}
