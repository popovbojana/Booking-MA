package com.example.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "owners")
public class Owner extends User{

    @OneToMany(mappedBy = "owner")
    private List<Accommodation> accommodations;

    @OneToMany(mappedBy = "owner")
    private List<RatingComment> ratingComments;

}
