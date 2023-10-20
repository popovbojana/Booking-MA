package com.example.booking.service;

import com.example.booking.dto.NewAccommodationDTO;
import com.example.booking.model.Accommodation;
import com.example.booking.model.Owner;
import com.example.booking.repository.AccommodationRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService implements IAccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, UserRepository userRepository) {
        this.accommodationRepository = accommodationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO) {
        Owner owner = (Owner) this.userRepository.findById(ownersId).get();

        Accommodation accommodation = new Accommodation(owner, newAccommodationDTO.getName(), newAccommodationDTO.getDescription(), newAccommodationDTO.getAmenities(), newAccommodationDTO.getMinGuests(), newAccommodationDTO.getMaxGuests(), newAccommodationDTO.getType(), newAccommodationDTO.getAvailableFrom(), newAccommodationDTO.getAvailableUntil(), newAccommodationDTO.getPricePerNight());
        this.accommodationRepository.save(accommodation);

        owner.getAccommodations().add(accommodation);
        this.userRepository.save(owner);
    }
}
