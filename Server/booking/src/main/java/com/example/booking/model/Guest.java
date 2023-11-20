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

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<RatingComment> ratingComments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> favoriteAccommodations;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> historyAccommodations;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Accommodation> reservedAccommodations;
}
