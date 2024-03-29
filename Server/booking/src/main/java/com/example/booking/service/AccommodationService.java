package com.example.booking.service;

import com.example.booking.dto.*;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.model.*;
import com.example.booking.model.enums.PriceType;
import com.example.booking.model.enums.ReservationState;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.*;
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
    private final ReservationRepository reservationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, UserRepository userRepository, AccommodationChangeRepository accommodationChangeRepository, AvailabilityPriceRepository availabilityPriceRepository, ReservationRepository reservationRepository) {
        this.accommodationRepository = accommodationRepository;
        this.userRepository = userRepository;
        this.accommodationChangeRepository = accommodationChangeRepository;
        this.availabilityPriceRepository = availabilityPriceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Accommodation> GetAll(){
        return this.accommodationRepository.findAll();
    }

    @Override
    public void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO) {
        Owner owner = (Owner) this.userRepository.findById(ownersId).get();

        List<AvailabilityPrice> availabilityPrices = new ArrayList<>();

        boolean autoApprove = false;
        Accommodation accommodation = new Accommodation(owner, newAccommodationDTO.getName(), newAccommodationDTO.getDescription(), newAccommodationDTO.getAmenities(), newAccommodationDTO.getMinGuests(), newAccommodationDTO.getMaxGuests(), newAccommodationDTO.getType(), newAccommodationDTO.getPriceType(), availabilityPrices, newAccommodationDTO.getCancellationDeadlineInDays(), newAccommodationDTO.getAddress(), newAccommodationDTO.getLatitude(), newAccommodationDTO.getLongitude(), newAccommodationDTO.getFinalRating(), newAccommodationDTO.getStandardPrice(), autoApprove);
        this.accommodationRepository.save(accommodation);

        for (NewAvailabilityPriceDTO ap : newAccommodationDTO.getAvailability()){
            AvailabilityPrice availabilityPrice = new AvailabilityPrice(accommodation, ap.getAmount(), ap.getDateFrom(), ap.getDateUntil());
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
            this.availabilityPriceRepository.deleteAll(accommodation.getAvailabilities());
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
                if (!changes.getName().equals("")){
                    accommodation.setName(changes.getName());
                }
                if (!changes.getDescription().equals("")){
                    accommodation.setDescription(changes.getDescription());
                }
                if (!changes.getAmenities().equals("")){
                    accommodation.setAmenities(changes.getAmenities());
                }
                if (changes.getMinGuests() != -1){
                    accommodation.setMinGuests(changes.getMinGuests());
                }
                if (changes.getMaxGuests() != -1){
                    accommodation.setMaxGuests(changes.getMaxGuests());
                }
                if (!changes.getType().equals("")){
                    accommodation.setType(changes.getType());
                }
                if (changes.getPriceType() == PriceType.PER_GUEST && accommodation.getPriceType() == PriceType.PER_UNIT){
                    accommodation.setPriceType(PriceType.PER_GUEST);
                }
                if (changes.getPriceType() == PriceType.PER_UNIT && accommodation.getPriceType() == PriceType.PER_GUEST){
                    accommodation.setPriceType(PriceType.PER_UNIT);
                }
                if (changes.getDateFrom() != null && changes.getDateUntil() != null && changes.getAmount() != -1.0){
                    AvailabilityPrice newAvailability = new AvailabilityPrice(accommodation, changes.getAmount(), changes.getDateFrom(), changes.getDateUntil());
                    this.availabilityPriceRepository.save(newAvailability);
                    List<AvailabilityPrice> accommodationAvailabilities = accommodation.getAvailabilities();
                    accommodationAvailabilities.add(newAvailability);
                }
                if (changes.getCancellationDeadlineInDays() != -1){
                    accommodation.setCancellationDeadlineInDays(changes.getCancellationDeadlineInDays());
                }
                if (changes.getStandardPrice() != -1.0){
                    accommodation.setStandardPrice(changes.getStandardPrice());
                }
                if (changes.isAutoApprove() != accommodation.isAutoApprove()){
                    accommodation.setAutoApprove(changes.isAutoApprove());
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
            return true;
            }
        }
        return false;
    }

    @Override
    public List<Accommodation> getAll() {
        return this.accommodationRepository.findAll();
    }

    @Override
    public void addToFavorites(FavoriteAccommodationDTO favAccommodation) throws NoDataWithId {
        if (this.userRepository.findById(favAccommodation.getGuestsId()).isPresent() && this.userRepository.findById(favAccommodation.getGuestsId()).get().getRole() == Role.GUEST){
            if (this.accommodationRepository.findById(favAccommodation.getAccommodationsId()).isPresent()){
                Guest guest = (Guest) this.userRepository.findById(favAccommodation.getGuestsId()).get();
                Accommodation accommodation = this.accommodationRepository.findById(favAccommodation.getAccommodationsId()).get();
                if (!guest.getFavoriteAccommodations().contains(accommodation)){
                    guest.getFavoriteAccommodations().add(accommodation);
                    this.userRepository.save(guest);
                } else {
                    throw new NoDataWithId("This accommodation is already in your favorites!");
                }
            } else {
                throw new NoDataWithId("There is no accommodation with this id!");
            }
        } else {
            throw new NoDataWithId("There is no guest with this id!");
        }
    }

    @Override
    public void removeFromFavorites(FavoriteAccommodationDTO favAccommodation) throws NoDataWithId {
        if (this.userRepository.findById(favAccommodation.getGuestsId()).isPresent() && this.userRepository.findById(favAccommodation.getGuestsId()).get().getRole() == Role.GUEST){
            if (this.accommodationRepository.findById(favAccommodation.getAccommodationsId()).isPresent()){
                Guest guest = (Guest) this.userRepository.findById(favAccommodation.getGuestsId()).get();
                Accommodation accommodation = this.accommodationRepository.findById(favAccommodation.getAccommodationsId()).get();
                if (guest.getFavoriteAccommodations().contains(accommodation)){
                    guest.getFavoriteAccommodations().remove(accommodation);
                    this.userRepository.save(guest);
                } else {
                    throw new NoDataWithId("There is no accommodation with this id in your favorites!");
                }
            } else {
                throw new NoDataWithId("There is no accommodation with this id!");
            }
        } else {
            throw new NoDataWithId("There is no guest with this id!");
        }
    }

    @Override
    public List<AccommodationDisplayDTO> getAllFavoritesForGuest(Long id) throws NoDataWithId {
        if (this.userRepository.findById(id).isPresent() && this.userRepository.findById(id).get().getRole() == Role.GUEST) {
            Guest guest = this.userRepository.findGuestById(id).get();
            List<AccommodationDisplayDTO> display = new ArrayList<>();
            for (Accommodation a : guest.getFavoriteAccommodations()){
                display.add(a.parseToDisplay());
            }
            return display;
        } else {
            throw new NoDataWithId("There is no guest with this id!");
        }
    }

    @Override
    public AccommodationDisplayDTO getAccommodationById(Long id) throws NoDataWithId {
        if (this.accommodationRepository.findById(id).isPresent()){
            Accommodation accommodation = this.accommodationRepository.findById(id).get();
            List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
            if(!accommodation.getReservations().isEmpty()){
                for(Reservation r : accommodation.getReservations()){
                    reservationDisplayDTOS.add(new ReservationDisplayDTO(r.getId(), r.getGuest().getId(), r.getOwner().getId(),r.getAccommodation().getId(),r.getCheckIn(),r.getCheckOut(),r.getGuestsNumber(),r.getTotalCost(),r.getReservationState(),r.getCancelationDeadline(), r.getGuestCancelationsNumber()));
                }
            }
            List<AvailabilityDisplayDTO> availabilities = new ArrayList<>();
            for (AvailabilityPrice ap : accommodation.getAvailabilities()){
                availabilities.add(ap.parseToDisplay());
            }
            return new AccommodationDisplayDTO(accommodation.getName(), accommodation.getDescription(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType(), accommodation.getPriceType(), accommodation.getCancellationDeadlineInDays(), accommodation.getStandardPrice(), accommodation.getAddress(), accommodation.getAmenities(), reservationDisplayDTOS, accommodation.isAutoApprove(), availabilities);
        } else {
            throw new NoDataWithId("There is no guests with this id!");
        }
    }
}
