package com.example.booking.dto;

import com.example.booking.model.enums.RatingCommentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateCommentDTO {
    private Long guestsId;
    private RatingCommentType type;
    private Long ownersId;
    private Long accommodationsId;
    private float rating;
    private String comment;
}
