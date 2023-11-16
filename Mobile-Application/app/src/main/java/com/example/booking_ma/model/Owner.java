package com.example.booking_ma.model;

import com.example.booking_ma.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public class Owner extends User{

    private List<Accommodation> accommodations;

    private List<RatingComment> ratingComments;

    public Owner(){
    }

    public Owner(String name, String surname) {
        super(name, surname);
    }

    public Owner(Long id, String email, String password, String name, String surname, String address, String phoneNumber, Role role, boolean activated, LocalDateTime activationLinkSent, boolean reported, String reportedReason, boolean blocked, List<RatingComment> ratingComments, List<Accommodation> accommodations, int passwordCharNumber) {
        super(id, email, password, name, surname, address, phoneNumber, role, activated, activationLinkSent, reported, reportedReason, blocked, passwordCharNumber);
        this.ratingComments = ratingComments;
        this.accommodations = accommodations;
    }
}
