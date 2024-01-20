package com.example.booking.model;

import com.example.booking.dto.AvailabilityDisplayDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "availability_prices")
public class AvailabilityPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    private double amount;

    private LocalDateTime dateFrom;

    private LocalDateTime dateUntil;

    public AvailabilityPrice(Accommodation accommodation, double amount, LocalDateTime dateFrom, LocalDateTime dateUntil){
        this.accommodation = accommodation;
        this.amount = amount;
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }

    public AvailabilityDisplayDTO parseToDisplay() {
        return new AvailabilityDisplayDTO(amount, dateFrom, dateUntil);
    }
}
