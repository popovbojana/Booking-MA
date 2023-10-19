package com.example.booking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Owner extends User{

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Accommodation> accommodations;

}
