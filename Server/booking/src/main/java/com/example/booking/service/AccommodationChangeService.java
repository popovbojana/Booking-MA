package com.example.booking.service;

import com.example.booking.dto.AccommodationChangesDTO;
import com.example.booking.dto.NewAvailabilityPriceDTO;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import com.example.booking.model.AvailabilityPrice;
import com.example.booking.repository.AccommodationChangeRepository;
import com.example.booking.repository.AccommodationRepository;
import com.example.booking.repository.AvailabilityPriceRepository;
import com.example.booking.service.interfaces.IAccommodationChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationChangeService implements IAccommodationChangeService {

    private final AccommodationChangeRepository accommodationChangeRepository;
    private final AccommodationRepository accommodationRepository;
    private final AvailabilityPriceRepository availabilityPriceRepository;

    @Autowired
    public AccommodationChangeService(AccommodationChangeRepository accommodationChangeRepository, AccommodationRepository accommodationRepository, AvailabilityPriceRepository availabilityPriceRepository){
        this.accommodationChangeRepository = accommodationChangeRepository;
        this.accommodationRepository = accommodationRepository;
        this.availabilityPriceRepository = availabilityPriceRepository;
    }

    @Override
    public void addAccommodationChange(Long id, AccommodationChangesDTO changes) {
        Accommodation accommodation = this.accommodationRepository.findById(id).get();
        List<AvailabilityPrice> availabilityPrices = new ArrayList<>();
        AccommodationChange accommodationChange = new AccommodationChange(accommodation, changes.getName(), changes.getDescription(), changes.getAmenities(), changes.getMinGuests(), changes.getMaxGuests(), changes.getType(), changes.getPriceType(), availabilityPrices, changes.getCancellationDeadlineInDays());
        this.accommodationChangeRepository.save(accommodationChange);
        for (NewAvailabilityPriceDTO dto : changes.getAvailabilities()){
            AvailabilityPrice availabilityPrice = new AvailabilityPrice(accommodation, accommodationChange, dto.getAmount(), dto.getDateFrom(), dto.getDateUntil());
            this.availabilityPriceRepository.save(availabilityPrice);
            availabilityPrices.add(availabilityPrice);
        }
        accommodation.setHasChanges(true);
        accommodation.setAccommodationChange(accommodationChange);
        this.accommodationRepository.save(accommodation);
    }

    @Override
    public List<AccommodationChange> getAll() {
        return this.accommodationChangeRepository.findAll();
    }
}
