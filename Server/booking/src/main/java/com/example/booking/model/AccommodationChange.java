package com.example.booking.model;

import com.example.booking.dto.AccommodationChangeDisplayDTO;
import com.example.booking.dto.AvailabilityDisplayDTO;
import com.example.booking.model.enums.PriceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String type;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    private int cancellationDeadlineInDays;

    private double standardPrice;
    private LocalDateTime dateFrom;
    private LocalDateTime dateUntil;
    private double amount;
    private boolean autoApprove;

    public AccommodationChange(Accommodation accommodation, String name, String description, String amenities, int minGuests, int maxGuests, String type, PriceType priceType, int cancellationDeadlineInDays, double standardPrice, LocalDateTime dateFrom, LocalDateTime dateUntil, double amount, boolean autoApprove){
        this.accommodation = accommodation;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.priceType = priceType;
        this.cancellationDeadlineInDays = cancellationDeadlineInDays;
        this.standardPrice = standardPrice;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.amount = amount;
        this.autoApprove = autoApprove;
    }

    public AccommodationChangeDisplayDTO parseToDisplay() {
        List<AvailabilityDisplayDTO> availabilityDisplayDTOS = new ArrayList<>();
        return new AccommodationChangeDisplayDTO(accommodation.getId(), name, description, amenities, minGuests, maxGuests, type, priceType, cancellationDeadlineInDays, standardPrice, dateFrom, dateUntil, amount);
    }
}
