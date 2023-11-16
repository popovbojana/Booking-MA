package com.example.booking.model;

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

    @OneToMany(mappedBy = "guest")
    private List<RatingComment> ratingComments;

    @ManyToMany
    private List<Accommodation> favoriteAccommodations;

    @ManyToMany
    private List<Accommodation> historyAccommodations;

    @ManyToMany
    private List<Accommodation> reservedAccommodations;
}
