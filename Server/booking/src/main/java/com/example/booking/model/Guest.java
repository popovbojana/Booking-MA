package com.example.booking.model;

import com.example.booking.dto.GuestDisplayDTO;
import com.example.booking.dto.UserDisplayDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "guests")
public class Guest extends User{

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<RatingComment> ratingComments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> favoriteAccommodations;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> historyAccommodations;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> reservedAccommodations;

    int cancelationsNumber;

    boolean blocked;

    public GuestDisplayDTO parseToDisplayGuest() {
        return new GuestDisplayDTO(super.getId(), super.getEmail(), super.getPassword(), super.getName(), super.getSurname(), super.getAddress(), super.getPhoneNumber(), super.getRole(), super.isActivated(), super.getActivationLinkSent(), super.isReported(), super.getReportedReason(), super.isBlocked(), super.getPasswordCharNumber(), cancelationsNumber);
    }
}
