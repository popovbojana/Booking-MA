package com.example.booking.model;

import com.example.booking.dto.AccommodationDisplayDTO;
import com.example.booking.model.enums.PriceType;
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
@Table(name = "accommodations")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;

    private String description;

    private String amenities;

    private int minGuests;

    private int maxGuests;

    private String type;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
    private List<AvailabilityPrice> availabilities;

    private int cancellationDeadlineInDays;

    private boolean approved;

    private boolean hasChanges;

    @OneToOne
    @JoinColumn(name = "change_id")
    private AccommodationChange accommodationChange;


    public Accommodation(Owner owner, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, List<AvailabilityPrice> availabilities, int cancellationDeadlineInDays){
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.availabilities = availabilities;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.approved = false;
        this.hasChanges = false;
        this.accommodationChange = null;
    }

    public AccommodationDisplayDTO parseToDisplay() {
        return new AccommodationDisplayDTO(id, owner.getId(), name, description, amenities, minGuests, maxGuests, approved, hasChanges);
    }
}
