package com.example.booking.service;

import com.example.booking.dto.AccommodationChangesDTO;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import com.example.booking.repository.AccommodationChangeRepository;
import com.example.booking.repository.AccommodationRepository;
import com.example.booking.service.interfaces.IAccommodationChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationChangeService implements IAccommodationChangeService {

    private final AccommodationChangeRepository accommodationChangeRepository;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationChangeService(AccommodationChangeRepository accommodationChangeRepository, AccommodationRepository accommodationRepository){
        this.accommodationChangeRepository = accommodationChangeRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public void addAccommodationChange(Long id, AccommodationChangesDTO changes) {
        Accommodation accommodation = this.accommodationRepository.findById(id).get();
        AccommodationChange accommodationChange = new AccommodationChange(accommodation, changes.getName(), changes.getDescription(), changes.getAmenities(), changes.getMinGuests(), changes.getMaxGuests());
        this.accommodationChangeRepository.save(accommodationChange);
        accommodation.setHasChanges(true);
        accommodation.setAccommodationChange(accommodationChange);
        this.accommodationRepository.save(accommodation);
    }

    @Override
    public List<AccommodationChange> getAll() {
        return this.accommodationChangeRepository.findAll();
    }
}
