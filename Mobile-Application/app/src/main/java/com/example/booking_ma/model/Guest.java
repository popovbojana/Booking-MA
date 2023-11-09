package com.example.booking_ma.model;

import java.io.Serializable;
import java.util.List;

public class Guest extends User {

    private List<RatingComment> ratingComments;

    private List<Accommodation> favoriteAccommodations;

    public Guest(List<RatingComment> ratingComments, List<Accommodation> favoriteAccommodations) {
        super();
        this.ratingComments = ratingComments;
        this.favoriteAccommodations = favoriteAccommodations;
    }


}
