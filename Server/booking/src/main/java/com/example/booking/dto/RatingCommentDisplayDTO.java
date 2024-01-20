package com.example.booking.dto;

import com.example.booking.model.enums.RatingCommentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingCommentDisplayDTO {
    private Long id;
    private Long guestsId;
    private String guest;
    private RatingCommentType type;
    private Long ownersId;
    private Long accommodationsId;
    private float rating;
    private String comment;
    private LocalDateTime time;
}
