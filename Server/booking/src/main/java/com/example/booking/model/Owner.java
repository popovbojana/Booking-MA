package com.example.booking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "owners")
public class Owner extends User{

    @OneToMany(mappedBy = "owner")
    private List<Accommodation> accommodations;

}
