package com.example.booking.service;

import com.example.booking.dto.*;
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
import java.time.Month;
import java.util.*;

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

    @Override
    public ReportAccommodationDTO getReportForAccommodation(Long id) throws NoDataWithId {
        if (this.accommodationRepository.findById(id).isPresent()){

            List<Reservation> allReservationsForAccommodations = this.reservationRepository.findAllReservationsForAccommodation(id);

            Map<String, Integer> reservationsByMonth = new HashMap<>();
            Map<String, Float> profitByMonth = new HashMap<>();

            int reservationsInYear = 0;
            float profitInYear = 0.0F;

            for (Reservation r : allReservationsForAccommodations) {
                LocalDateTime checkIn = r.getCheckIn();
                LocalDateTime checkOut = r.getCheckOut();

                if (checkIn.getMonth() == Month.JANUARY && checkOut.getMonth() == Month.JANUARY) {
                    reservationsByMonth.put("January", reservationsByMonth.getOrDefault("January", 0) + 1);
                    profitByMonth.put("January", profitByMonth.getOrDefault("January", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.FEBRUARY && checkOut.getMonth() == Month.FEBRUARY) {
                    reservationsByMonth.put("February", reservationsByMonth.getOrDefault("February", 0) + 1);
                    profitByMonth.put("February", profitByMonth.getOrDefault("February", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.MARCH && checkOut.getMonth() == Month.MARCH) {
                    reservationsByMonth.put("March", reservationsByMonth.getOrDefault("March", 0) + 1);
                    profitByMonth.put("March", profitByMonth.getOrDefault("March", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.APRIL && checkOut.getMonth() == Month.APRIL) {
                    reservationsByMonth.put("April", reservationsByMonth.getOrDefault("April", 0) + 1);
                    profitByMonth.put("April", profitByMonth.getOrDefault("April", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.MAY && checkOut.getMonth() == Month.MAY) {
                    reservationsByMonth.put("May", reservationsByMonth.getOrDefault("May", 0) + 1);
                    profitByMonth.put("May", profitByMonth.getOrDefault("May", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.JUNE && checkOut.getMonth() == Month.JUNE) {
                    reservationsByMonth.put("June", reservationsByMonth.getOrDefault("June", 0) + 1);
                    profitByMonth.put("June", profitByMonth.getOrDefault("June", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.JULY && checkOut.getMonth() == Month.JULY) {
                    reservationsByMonth.put("July", reservationsByMonth.getOrDefault("July", 0) + 1);
                    profitByMonth.put("July", profitByMonth.getOrDefault("July", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }
                if (checkIn.getMonth() == Month.AUGUST && checkOut.getMonth() == Month.AUGUST) {
                    reservationsByMonth.put("August", reservationsByMonth.getOrDefault("August", 0) + 1);
                    profitByMonth.put("August", profitByMonth.getOrDefault("August", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.SEPTEMBER && checkOut.getMonth() == Month.SEPTEMBER) {
                    reservationsByMonth.put("September", reservationsByMonth.getOrDefault("September", 0) + 1);
                    profitByMonth.put("September", profitByMonth.getOrDefault("September", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.OCTOBER && checkOut.getMonth() == Month.OCTOBER) {
                    reservationsByMonth.put("October", reservationsByMonth.getOrDefault("October", 0) + 1);
                    profitByMonth.put("October", profitByMonth.getOrDefault("October", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.NOVEMBER && checkOut.getMonth() == Month.NOVEMBER) {
                    reservationsByMonth.put("November", reservationsByMonth.getOrDefault("November", 0) + 1);
                    profitByMonth.put("November", profitByMonth.getOrDefault("November", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

                if (checkIn.getMonth() == Month.DECEMBER && checkOut.getMonth() == Month.DECEMBER) {
                    reservationsByMonth.put("December", reservationsByMonth.getOrDefault("December", 0) + 1);
                    profitByMonth.put("December", profitByMonth.getOrDefault("December", (float) 0.0) + r.getTotalCost());
                    reservationsInYear += 1;
                    profitInYear += r.getTotalCost();
                }

            }

            ReportAccommodationDTO report = new ReportAccommodationDTO(
                    reservationsInYear,
                    profitInYear,
                    reservationsByMonth.getOrDefault("January", 0),
                    profitByMonth.getOrDefault("January", 0.0f),
                    reservationsByMonth.getOrDefault("February", 0),
                    profitByMonth.getOrDefault("February", 0.0f),
                    reservationsByMonth.getOrDefault("March", 0),
                    profitByMonth.getOrDefault("March", 0.0f),
                    reservationsByMonth.getOrDefault("April", 0),
                    profitByMonth.getOrDefault("April", 0.0f),
                    reservationsByMonth.getOrDefault("May", 0),
                    profitByMonth.getOrDefault("May", 0.0f),
                    reservationsByMonth.getOrDefault("June", 0),
                    profitByMonth.getOrDefault("June", 0.0f),
                    reservationsByMonth.getOrDefault("July", 0),
                    profitByMonth.getOrDefault("July", 0.0f),
                    reservationsByMonth.getOrDefault("August", 0),
                    profitByMonth.getOrDefault("August", 0.0f),
                    reservationsByMonth.getOrDefault("September", 0),
                    profitByMonth.getOrDefault("September", 0.0f),
                    reservationsByMonth.getOrDefault("October", 0),
                    profitByMonth.getOrDefault("October", 0.0f),
                    reservationsByMonth.getOrDefault("November", 0),
                    profitByMonth.getOrDefault("November", 0.0f),
                    reservationsByMonth.getOrDefault("December", 0),
                    profitByMonth.getOrDefault("December", 0.0f)
            );
            return report;

        } else {
            throw new NoDataWithId("There is no accommodation with this id!");
        }
    }

    @Override
    public ReportAllAccommodationsDTO getReportForAllAccommodations(Long id, ReportRangeDTO range) throws NoDataWithId {
        if (this.userRepository.findById(id).isPresent()){
            List<Reservation> allOwnerReservations = this.reservationRepository.findAllApprovedReservationsByOwnerId(id);
            List<Accommodation> allAccommodations = this.accommodationRepository.findAllAccommodationsForOwner(id);

            HashMap<String, Integer> reservationsMap = new HashMap<>();
            HashMap<String, Float> profitMap = new HashMap<>();

            for (Accommodation accommodation : allAccommodations) {
                String accommodationName = accommodation.getName();
                reservationsMap.put(accommodationName, 0);
                profitMap.put(accommodationName, 0.0f);
            }

            for (Reservation r : allOwnerReservations) {
//                if (r.getCheckIn().isAfter(range.getFrom()) && r.getCheckOut().isAfter(range.getFrom()) && r.getCheckIn().isBefore(range.getUntil()) && r.getCheckOut().isBefore(range.getUntil())){
                if (r.getCheckIn().isAfter(range.getFrom()) && r.getCheckOut().isBefore(range.getUntil())){
                    String accommodationName = r.getAccommodation().getName();

                    if (reservationsMap.containsKey(accommodationName)) {
                        int currentReservationCount = reservationsMap.get(accommodationName);
                        reservationsMap.put(accommodationName, currentReservationCount + 1);

                        float currentProfit = profitMap.get(accommodationName);
                        float reservationCost = r.getTotalCost();
                        profitMap.put(accommodationName, currentProfit + reservationCost);
                    }
                }
            }
            return new ReportAllAccommodationsDTO(reservationsMap, profitMap);

        } else {
            throw new NoDataWithId("There is no owner with this id!");
        }
    }
}