package com.example.booking_ma.model;

import com.example.booking_ma.model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Guest extends User {

    private List<RatingComment> ratingComments;

    private List<Accommodation> favoriteAccommodations;

    public Guest(String name, String surname) {
        super(name, surname);
    }

    public Guest(Long id, String email, String password, String name, String surname, String address, String phoneNumber, Role role, boolean activated, LocalDateTime activationLinkSent, boolean reported, String reportedReason, boolean blocked, List<RatingComment> ratingComments, List<Accommodation> favoriteAccommodations) {
        super(id, email, password, name, surname, address, phoneNumber, role, activated, activationLinkSent, reported, reportedReason, blocked);
        this.ratingComments = ratingComments;
        this.favoriteAccommodations = favoriteAccommodations;
    }


}
