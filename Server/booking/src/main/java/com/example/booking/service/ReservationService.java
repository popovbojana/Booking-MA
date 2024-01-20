package com.example.booking.service;

import com.example.booking.dto.CheckAccommodationAvailabilityDTO;
import com.example.booking.dto.ReservationDTO;
import com.example.booking.dto.ReservationDisplayDTO;
import com.example.booking.dto.SearchDTO;
import com.example.booking.exceptions.AlreadyChangedState;
import com.example.booking.exceptions.DeadlineException;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.RequirementNotSatisfied;
import com.example.booking.model.*;
import com.example.booking.model.enums.ReservationState;
import com.example.booking.repository.*;
import com.example.booking.service.interfaces.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    private final AvailabilityPriceRepository availabilityPriceRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, AccommodationRepository accommodationRepository, AvailabilityPriceRepository availabilityPriceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.availabilityPriceRepository = availabilityPriceRepository;
    }

    @Override
    public Optional<Reservation> getReservation(Long id){
        return this.reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> GetAll(){
        return this.reservationRepository.findAll();
    }

    @Override
    public void createReservation(ReservationDTO reservationRequest) throws NoDataWithId {
        if (!this.userRepository.findGuestById(reservationRequest.getGuestId()).isPresent()){
            throw new NoDataWithId("There is no guest with this id!");
        }
        if (!this.userRepository.findOwnerById(reservationRequest.getOwnerId()).isPresent()){
            throw new NoDataWithId("There is no owner with this id!");
        }
        if(!this.accommodationRepository.findById(reservationRequest.getAccommodationId()).isPresent()){
            throw new NoDataWithId("There is no accommodation with this id");
        }
        Guest guest = this.userRepository.findGuestById(reservationRequest.getGuestId()).get();
        Owner owner = this.userRepository.findOwnerById(reservationRequest.getOwnerId()).get();
        Accommodation accommodation = this.accommodationRepository.findById(reservationRequest.getAccommodationId()).get();

        if(reservationRequest.getGuestsNumber() < accommodation.getMinGuests()){
            throw new NoDataWithId("Guests are not in interval range");
        }
        if(reservationRequest.getGuestsNumber() > accommodation.getMaxGuests()){
            throw new NoDataWithId("Guests are not in interval range");
        }

        ReservationState reservationState;

        if(accommodation.isAutoApprove()){
        //OVO VEROVATNO RADI
            List<Reservation> reservations = this.reservationRepository.findPendingReservationsBetweenDates(reservationRequest.getCheckIn(), reservationRequest.getCheckOut());
            for(Reservation r : reservations){
                r.setReservationState(ReservationState.DENIED);
                this.reservationRepository.save(r);
            }
        //TU SE ZAVRSAVA, MOZDA I NEPOTRBNO
            reservationState = ReservationState.APPROVED;
        }
        else{
            reservationState = ReservationState.PENDING;
        }

        LocalDateTime cancelationDeadline = reservationRequest.getCheckIn().minusDays(accommodation.getCancellationDeadlineInDays());
        Reservation reservation = new Reservation(guest, owner, accommodation, reservationRequest.getCheckIn(), reservationRequest.getCheckOut(), reservationRequest.getGuestsNumber(), reservationRequest.getTotalCost(), reservationState, cancelationDeadline, guest.getCancelationsNumber());
        this.reservationRepository.save(reservation);

        List<Reservation> reservations = accommodation.getReservations();
        reservations.add(reservation);
        accommodation.setReservations(reservations);

        this.accommodationRepository.save(accommodation);
    }

    @Override
    public void approveReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState {
        if(!this.reservationRepository.findById(reservationId).isPresent()){
            throw new NoDataWithId("There is no reservation with this id");
        }
        Reservation reservation = this.reservationRepository.findById(reservationId).get();
        if(reservation.getReservationState().equals(ReservationState.APPROVED) || reservation.getReservationState().equals(ReservationState.DENIED)) {
            throw new NoDataWithId("Reservation already approved or denied");
        }
        if(reservation.getReservationState().equals(ReservationState.CANCELED)){
            throw new AlreadyChangedState("Reservation already canceled");
        }

        List<Reservation> reservations = this.reservationRepository.findPendingReservationsBetweenDates(reservation.getCheckIn(), reservation.getCheckOut());
        for(Reservation r : reservations){
            if(reservation.getId() != r.getId()){
                r.setReservationState(ReservationState.DENIED);
                this.reservationRepository.save(r);
            }
        }

        reservation.setReservationState(ReservationState.APPROVED);
        this.reservationRepository.save(reservation);
    }

    @Override
    public void denyReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState {
        if(!this.reservationRepository.findById(reservationId).isPresent()){
            throw new NoDataWithId("There is no reservation with this id");
        }
        Reservation reservation = this.reservationRepository.findById(reservationId).get();
        if(reservation.getReservationState().equals(ReservationState.APPROVED) || reservation.getReservationState().equals(ReservationState.DENIED)){
            throw new NoDataWithId("Reservation already approved or denied");
        }
        if(reservation.getReservationState().equals(ReservationState.CANCELED)){
            throw new AlreadyChangedState("Reservation already canceled");
        }
        reservation.setReservationState(ReservationState.DENIED);
        this.reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState, DeadlineException {
        if(!this.reservationRepository.findById(reservationId).isPresent()){
            throw new NoDataWithId("There is no reservation with this id");
        }
        Reservation reservation = this.reservationRepository.findById(reservationId).get();
        if(reservation.getReservationState().equals(ReservationState.DENIED)){
            throw new NoDataWithId("Reservation already denied reservation");
        }
        if(reservation.getReservationState().equals(ReservationState.CANCELED)){
            throw new AlreadyChangedState("Reservation already canceled");
        }
        if(LocalDateTime.now().isAfter(reservation.getCancelationDeadline())){
            throw new DeadlineException("Passed deadline");
        }

        Guest guest = reservation.getGuest();
        int cancelationNumber = guest.getCancelationsNumber();
        cancelationNumber += 1;
        guest.setCancelationsNumber(cancelationNumber);
        this.userRepository.save(guest);

        reservation.setReservationState(ReservationState.DENIED);
        this.reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) throws NoDataWithId, AlreadyChangedState {
        if(!this.reservationRepository.findById(reservationId).isPresent()){
            throw new NoDataWithId("There is no reservation with this id");
        }
        Reservation reservation = this.reservationRepository.findById(reservationId).get();
        if(reservation.getReservationState().equals(ReservationState.APPROVED)){
            throw new AlreadyChangedState("Cant delete approved reservation");
        }
        if(reservation.getReservationState().equals(ReservationState.DENIED)){
            throw new AlreadyChangedState("Cant delete denied reservation");
        }
        if(reservation.getReservationState().equals(ReservationState.CANCELED)){
            throw new AlreadyChangedState("Cant delete canceled reservation");
        }

        this.reservationRepository.delete(reservation);
    }

    @Override
    public List<ReservationDisplayDTO> getGuestReservations(Long guestId) throws NoDataWithId {
        if (!this.userRepository.findGuestById(guestId).isPresent()){
            throw new NoDataWithId("There is no guest with this id!");
        }
        List<Reservation> guestReservations = this.reservationRepository.findAllReservationsByGuestId(guestId);

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : guestReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }

    @Override
    public List<ReservationDisplayDTO> getGuestReservationsBySearch(Long guestId, SearchDTO searchRequest) throws NoDataWithId {
        if (!this.userRepository.findGuestById(guestId).isPresent()){
            throw new NoDataWithId("There is no guest with this id!");
        }
        List<Reservation> guestReservations = this.reservationRepository.findAllReservationsByGuestIdAndAccommodationNameAndBetweenDates(guestId, searchRequest.getAccommodationName(), searchRequest.getDateFrom(), searchRequest.getDateUntil());
        System.out.println(guestReservations.size());

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : guestReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }


    @Override
    public List<ReservationDisplayDTO> getGuestPendingReservations(Long guestId) throws NoDataWithId {
        if (!this.userRepository.findGuestById(guestId).isPresent()){
            throw new NoDataWithId("There is no guest with this id!");
        }
        List<Reservation> guestReservations = this.reservationRepository.findAllPendingByGuestId(guestId);

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : guestReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }

    @Override
    public List<ReservationDisplayDTO> getOwnerReservations(Long ownerId) throws NoDataWithId {
        if (!this.userRepository.findOwnerById(ownerId).isPresent()){
            throw new NoDataWithId("There is no owner with this id!");
        }
        List<Reservation> ownerReservations = this.reservationRepository.findAllByOwnerId(ownerId);

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : ownerReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }

    @Override
    public List<ReservationDisplayDTO> getOwnerReservationsBySearch(Long ownerId, SearchDTO searchRequest) throws NoDataWithId {
        if (!this.userRepository.findOwnerById(ownerId).isPresent()){
            throw new NoDataWithId("There is no owner with this id!");
        }
        List<Reservation> guestReservations = this.reservationRepository.findAllReservationsByOwnerIdAndAccommodationNameAndBetweenDates(ownerId, searchRequest.getAccommodationName(), searchRequest.getDateFrom(), searchRequest.getDateUntil());

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : guestReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }

    @Override
    public List<ReservationDisplayDTO> getOwnerPendingReservations(Long ownerId) throws NoDataWithId {
        if (!this.userRepository.findOwnerById(ownerId).isPresent()){
            throw new NoDataWithId("There is no owner with this id!");
        }
        List<Reservation> ownerReservations = this.reservationRepository.findAllPendingByOwnerId(ownerId);

        List<ReservationDisplayDTO> reservationDisplayDTOS = new ArrayList<>();
        for (Reservation r : ownerReservations){
            reservationDisplayDTOS.add(r.parseToDisplay());
        }
        return reservationDisplayDTOS;
    }

    @Override
    public boolean checkAccommodationInDateInterval(CheckAccommodationAvailabilityDTO checkAccommodationAvailabilityRequest) throws NoDataWithId, RequirementNotSatisfied {
        if (!this.accommodationRepository.findById(checkAccommodationAvailabilityRequest.getAccommodationId()).isPresent()){
            throw new NoDataWithId("There is no accommodation with this id!");
        }
        Accommodation accommodation = this.accommodationRepository.findById(checkAccommodationAvailabilityRequest.getAccommodationId()).get();
        List<AvailabilityPrice> availabilityPrices = this.availabilityPriceRepository.findAvailabilityPricesByDateRangeAndAccommodationId(checkAccommodationAvailabilityRequest.getAccommodationId(), checkAccommodationAvailabilityRequest.getCheckIn(), checkAccommodationAvailabilityRequest.getCheckOut());
        if(availabilityPrices.isEmpty()){
            throw new RequirementNotSatisfied("There is no available accommodation in this datetime");
//            return false;
        }

        List<Reservation> reservations = this.reservationRepository.findApprovedReservationsBetweenDates(checkAccommodationAvailabilityRequest.getCheckIn(), checkAccommodationAvailabilityRequest.getCheckOut());
        if(reservations.size() >= 1){
            throw new RequirementNotSatisfied("There is already approved reservation in this date range");
//            return false;
        }

        return true;
    }
}