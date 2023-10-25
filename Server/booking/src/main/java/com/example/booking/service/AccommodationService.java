package com.example.booking.service;

import com.example.booking.dto.NewAccommodationDTO;
import com.example.booking.dto.NewAvailabilityPriceDTO;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import com.example.booking.model.AvailabilityPrice;
import com.example.booking.model.Owner;
import com.example.booking.repository.AccommodationChangeRepository;
import com.example.booking.repository.AccommodationRepository;
import com.example.booking.repository.AvailabilityPriceRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    private final AccommodationChangeRepository accommodationChangeRepository;
    private final AvailabilityPriceRepository availabilityPriceRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, UserRepository userRepository, AccommodationChangeRepository accommodationChangeRepository, AvailabilityPriceRepository availabilityPriceRepository) {
        this.accommodationRepository = accommodationRepository;
        this.userRepository = userRepository;
        this.accommodationChangeRepository = accommodationChangeRepository;
        this.availabilityPriceRepository = availabilityPriceRepository;
    }

    @Override
    public void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO) {
        Owner owner = (Owner) this.userRepository.findById(ownersId).get();

        List<AvailabilityPrice> availabilityPrices = new ArrayList<>();

        Accommodation accommodation = new Accommodation(owner, newAccommodationDTO.getName(), newAccommodationDTO.getDescription(), newAccommodationDTO.getAmenities(), newAccommodationDTO.getMinGuests(), newAccommodationDTO.getMaxGuests(), newAccommodationDTO.getType(), newAccommodationDTO.getPriceType(), availabilityPrices, newAccommodationDTO.getCancellationDeadlineInDays());
        this.accommodationRepository.save(accommodation);

        for (NewAvailabilityPriceDTO ap : newAccommodationDTO.getAvailability()){
            AvailabilityPrice availabilityPrice = new AvailabilityPrice(accommodation, null, ap.getAmount(), ap.getDateFrom(), ap.getDateUntil());
            availabilityPrices.add(availabilityPrice);
            this.availabilityPriceRepository.save(availabilityPrice);
        }
        accommodation.setAvailabilities(availabilityPrices);
        this.accommodationRepository.save(accommodation);

        owner.getAccommodations().add(accommodation);
        this.userRepository.save(owner);
    }

    @Override
    public List<Accommodation> getAllAccommodationsForOwner(Long ownersId) {
        return this.accommodationRepository.findAllAccommodationsForOwner(ownersId);
    }

    @Override
    public List<Accommodation> getAllNew() {
        return this.accommodationRepository.findAllNewAccommodation();
    }

    @Override
    public List<Accommodation> getAllChanges() {
        return this.accommodationRepository.findAllAccommodationsWithChange();
    }

    @Override
    public boolean approveNewAccommodation(Long id, boolean approval) {
        if (this.accommodationRepository.findById(id).isPresent()){
            Accommodation accommodation = this.accommodationRepository.findById(id).get();
            if (approval){
                accommodation.setApproved(true);
                this.accommodationRepository.save(accommodation);
                return true;
            }
            Owner owner = (Owner) this.userRepository.findById(accommodation.getOwner().getId()).get();
            owner.getAccommodations().remove(accommodation);
            this.userRepository.save(owner);
            this.accommodationRepository.delete(accommodation);
            return true;
        }
        return false;
    }

    @Override
    public boolean approveAccommodationChanges(Long id, boolean approval) {
        if (this.accommodationRepository.findById(id).isPresent()){
            Accommodation accommodation = this.accommodationRepository.findById(id).get();
            if (approval){
                AccommodationChange changes = accommodation.getAccommodationChange();
                if (changes.getName() != null){
                    accommodation.setName(changes.getName());
                }
                if (changes.getDescription() != null){
                    accommodation.setDescription(changes.getDescription());
                }
                if (changes.getAmenities() != null){
                    accommodation.setAmenities(changes.getAmenities());
                }
                if (changes.getMinGuests() != -1){
                    accommodation.setMinGuests(changes.getMinGuests());
                }
                if (changes.getMaxGuests() != -1){
                    accommodation.setMaxGuests(changes.getMaxGuests());
                }
                if (changes.getPriceType() != null){
                    accommodation.setPriceType(changes.getPriceType());
                }
                if (changes.getAvailabilities() != null && !changes.getAvailabilities().isEmpty()){
                    this.availabilityPriceRepository.deleteAll(accommodation.getAvailabilities());
                    List<AvailabilityPrice> newAvailabilityPrices = new ArrayList<>();
                    for (AvailabilityPrice ap : changes.getAvailabilities()){
                        this.availabilityPriceRepository.delete(ap);
                        AvailabilityPrice newAp = new AvailabilityPrice(ap.getAccommodation(), null, ap.getAmount(), ap.getDateFrom(), ap.getDateUntil());
                        newAvailabilityPrices.add(newAp);
                    }
                    accommodation.setAvailabilities(newAvailabilityPrices);
                    this.availabilityPriceRepository.saveAll(newAvailabilityPrices);
                }
                if (changes.getCancellationDeadlineInDays() != -1){
                    accommodation.setCancellationDeadlineInDays(changes.getCancellationDeadlineInDays());
                }
                accommodation.setAccommodationChange(null);
                accommodation.setHasChanges(false);
                this.accommodationRepository.save(accommodation);
                this.accommodationChangeRepository.delete(changes);
                return true;
            }
            AccommodationChange accommodationChange = this.accommodationChangeRepository.findById(accommodation.getAccommodationChange().getId()).orElse(null);
            if (accommodationChange != null) {
                List<Accommodation> accommodationsWithChange = this.accommodationRepository.findByAccommodationChange(accommodationChange);
                for (Accommodation a : accommodationsWithChange) {
                    a.setAccommodationChange(null);
                    a.setHasChanges(false);

                    this.availabilityPriceRepository.deleteAll(a.getAvailabilities());
                }
                this.accommodationChangeRepository.delete(accommodationChange);

            }
        }
        return false;
    }

    @Override
    public List<Accommodation> getAll() {
        return this.accommodationRepository.findAll();
    }
}
