package com.example.booking.model;

import com.example.booking.dto.AccommodationChangeDisplayDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "accommodation_changes")
public class AccommodationChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "accommodationChange")
    private Accommodation accommodation;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    public AccommodationChange(Accommodation accommodation, String name, String description, String amenities, int minGuests, int maxGuests){
        this.accommodation = accommodation;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }

    public AccommodationChangeDisplayDTO parseToDisplay() {
        return new AccommodationChangeDisplayDTO(accommodation.getId(), name, description, amenities, minGuests, maxGuests);
    }
}
