package com.example.booking.service;

import com.example.booking.dto.AllRatingsDisplay;
import com.example.booking.dto.ApprovalDTO;
import com.example.booking.dto.RateCommentDTO;
import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.RequirementNotSatisfied;
import com.example.booking.model.*;
import com.example.booking.model.enums.RatingCommentType;
import com.example.booking.model.enums.ReservationState;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.*;
import com.example.booking.service.interfaces.IRatingCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingCommentService implements IRatingCommentService {

    private final RatingCommentRepository ratingCommentRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RatingCommentService(RatingCommentRepository ratingCommentRepository, UserRepository userRepository, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {
        this.ratingCommentRepository = ratingCommentRepository;
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void rateComment(RateCommentDTO rateCommentDTO) throws NoDataWithId, RequirementNotSatisfied {
        if (this.userRepository.findById(rateCommentDTO.getGuestsId()).isPresent() && this.userRepository.findById(rateCommentDTO.getGuestsId()).get().getRole() == Role.GUEST){
            Guest guest = (Guest) this.userRepository.findById(rateCommentDTO.getGuestsId()).get();
            if (rateCommentDTO.getType() == RatingCommentType.FOR_OWNER) {
                if (this.userRepository.findById(rateCommentDTO.getOwnersId()).isPresent() && this.userRepository.findById(rateCommentDTO.getOwnersId()).get().getRole() == Role.OWNER){

                    boolean canComment = false;
                    List<Reservation> allGuestsReservations = this.reservationRepository.findAllReservationsByGuestId(guest.getId());
                    for (Reservation r : allGuestsReservations){
                        System.out.println("Checkout:" + r.getCheckOut());
                        LocalDateTime currentTime = LocalDateTime.now();
                        System.out.println("Current time: " + currentTime);
                        if (r.getCheckOut().isBefore(currentTime) && r.getReservationState() == ReservationState.APPROVED){
                            canComment = true;
                        }
                        System.out.println(canComment);
                    }

                    if (!canComment) {
                        throw new RequirementNotSatisfied("You must have at least one approved reservation in the past with this owner to be able to leave rating and comment!");
                    }

                    Owner owner = (Owner) this.userRepository.findById(rateCommentDTO.getOwnersId()).get();
                    RatingComment ratingComment = new RatingComment(rateCommentDTO.getType(), guest, owner, null, rateCommentDTO.getRating(), rateCommentDTO.getComment());
                    this.ratingCommentRepository.save(ratingComment);
                    guest.getRatingComments().add(ratingComment);
                    this.userRepository.save(guest);
                    owner.getRatingComments().add(ratingComment);
                    this.userRepository.save(owner);
                } else {
                    throw new NoDataWithId("There is no owner with this id!");
                }
            } else {
                if (this.accommodationRepository.findById(rateCommentDTO.getAccommodationsId()).isPresent()){

                    boolean canComment = false;
                    List<Reservation> allGuestsReservations = this.reservationRepository.findAllReservationsByGuestId(guest.getId());
                    for (Reservation r : allGuestsReservations){
                        LocalDateTime currentTime = LocalDateTime.now();
                        if (r.getCheckOut().isBefore(currentTime) && r.getReservationState() == ReservationState.APPROVED && currentTime.isBefore(r.getCheckOut().plusDays(7))) {
                            if (r.getAccommodation().getId() == rateCommentDTO.getAccommodationsId()){
                                canComment = true;
                            }
                        }
                    }

                    if (!canComment) {
                        throw new RequirementNotSatisfied("You must have at least one approved reservation in the past for this accommodation to be able to leave rating and comment!");
                    }

                    Accommodation accommodation = this.accommodationRepository.findById(rateCommentDTO.getAccommodationsId()).get();
                    RatingComment ratingComment = new RatingComment(rateCommentDTO.getType(), guest, null, accommodation, rateCommentDTO.getRating(), rateCommentDTO.getComment());
                    this.ratingCommentRepository.save(ratingComment);
                    guest.getRatingComments().add(ratingComment);
                    this.userRepository.save(guest);
                } else {
                    throw new NoDataWithId("There is no accommodation with this id!");
                }
            }
        } else {
            throw new NoDataWithId("There is no guest with this id!");
        }
    }

    @Override
    public void deleteRatingComment(Long id) throws NoDataWithId {
        if (this.ratingCommentRepository.findById(id).isPresent()){
            RatingComment ratingComment = this.ratingCommentRepository.findById(id).get();
            if (ratingComment.getType() == RatingCommentType.FOR_OWNER){
                Owner owner = ratingComment.getOwner();
                owner.getRatingComments().remove(ratingComment);
                this.userRepository.save(owner);
            } else {
                Accommodation accommodation = ratingComment.getAccommodation();
                accommodation.getRatingComments().remove(ratingComment);
                this.accommodationRepository.save(accommodation);
            }
            this.ratingCommentRepository.delete(ratingComment);
        } else {
            throw new NoDataWithId("There is no rating and comment with this id!");
        }
    }

    @Override
    public List<RatingCommentDisplayDTO> getAllForGuest(Long id) {
        Iterable<RatingComment> all = this.ratingCommentRepository.findAll();
        List<RatingCommentDisplayDTO> allDisplay = new ArrayList<>();
        for (RatingComment rc : all){
            allDisplay.add(rc.parseToDisplay());
        }
        return allDisplay;
    }

    @Override
    public AllRatingsDisplay getAllForOwner(Long id) throws NoDataWithId {
        if (this.userRepository.findById(id).isPresent() && this.userRepository.findById(id).get().getRole() == Role.OWNER){
            List<RatingComment> all = this.ratingCommentRepository.findAllForOwner(id);
            List<RatingCommentDisplayDTO> allForDisplay = new ArrayList<>();
            double total = 0;
            for (RatingComment rc : all){
                allForDisplay.add(rc.parseToDisplay());
                total += rc.getRating();
            }
            return new AllRatingsDisplay((float)total/all.size(), allForDisplay);
        } else {
            throw new NoDataWithId("There is no owner with this id!");
        }
    }

    @Override
    public AllRatingsDisplay getAllForAccommodation(Long id) throws NoDataWithId {
        if (this.accommodationRepository.findById(id).isPresent()){
            List<RatingComment> all = this.ratingCommentRepository.findAllForAccommodation(id);
            List<RatingCommentDisplayDTO> allForDisplay = new ArrayList<>();
            double total = 0;
            for (RatingComment rc : all){
                allForDisplay.add(rc.parseToDisplay());
                total += rc.getRating();
            }
            return new AllRatingsDisplay((float)total/all.size(), allForDisplay);
        } else {
            throw new NoDataWithId("There is no accommodation with this id!");
        }
    }

    @Override
    public void report(Long id) throws NoDataWithId {
        if (this.ratingCommentRepository.findById(id).isPresent()){
            RatingComment ratingComment = this.ratingCommentRepository.findById(id).get();
            ratingComment.setReported(true);
            this.ratingCommentRepository.save(ratingComment);
        } else {
            throw new NoDataWithId("There is no rating and comment with this id!");
        }
    }

    @Override
    public List<RatingCommentDisplayDTO> getReportedOwnersComments() throws NoDataWithId {
        List<RatingComment> reportedComments = this.ratingCommentRepository.findAllReportedComments();
        List<RatingCommentDisplayDTO> reportedCommentDisplayDTOS = new ArrayList<>();
        for(RatingComment r : reportedComments){
            if(r.getType() == RatingCommentType.FOR_OWNER) {
                reportedCommentDisplayDTOS.add(r.parseToDisplay());
            }
        }
        return reportedCommentDisplayDTOS;
    }

    @Override
    public List<RatingCommentDisplayDTO> getReportedComments() throws NoDataWithId {
        List<RatingComment> reportedComments = this.ratingCommentRepository.findAllReportedComments();
        List<RatingCommentDisplayDTO> reportedCommentDisplayDTOS = new ArrayList<>();
        for(RatingComment r : reportedComments){
            reportedCommentDisplayDTOS.add(r.parseToDisplay());
        }
        return reportedCommentDisplayDTOS;
    }

    @Override
    public boolean handleReportedComment(Long ratingCommentId, ApprovalDTO approval) throws NoDataWithId, RequirementNotSatisfied {
        if(!this.ratingCommentRepository.findById(ratingCommentId).isPresent()){
            throw new NoDataWithId("There is no rating and comment with this id!");
        }
        RatingComment reportedComment = this.ratingCommentRepository.findById(ratingCommentId).get();
        if(!reportedComment.isReported()){
            throw new RequirementNotSatisfied("Cant delete unreported comment");
        }
        if(approval.isApproval()){
            this.ratingCommentRepository.delete(reportedComment);
        }
        else{
            reportedComment.setReported(false);
            this.ratingCommentRepository.save(reportedComment);
        }

        if(approval.isApproval()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public float calculateAverageRating(List<RatingComment> allRatings) {
        float sum = 0;
        for (RatingComment rt : allRatings) {
            sum += rt.getRating();
        }
        return sum/allRatings.size();
    }

    @Override
    public List<RatingCommentDisplayDTO> getAllUnapproved() {
        List<RatingComment> all = this.ratingCommentRepository.getAllUnapproved();
        List<RatingCommentDisplayDTO> allForDisplay = new ArrayList<>();
        double total = 0;
        for (RatingComment rc : all){
            allForDisplay.add(rc.parseToDisplay());
            total += rc.getRating();
        }
        return allForDisplay;
    }

    @Override
    public void approve(Long id, boolean approval) throws NoDataWithId {
        if (this.ratingCommentRepository.findById(id).isPresent()){
            RatingComment ratingComment = this.ratingCommentRepository.findById(id).get();
            if (approval){
                ratingComment.setApproved(true);
                this.ratingCommentRepository.save(ratingComment);
                Accommodation accommodation = ratingComment.getAccommodation();
                accommodation.setFinalRating(calculateAverageRating(accommodation.getRatingComments()));
                this.accommodationRepository.save(accommodation);
            } else {
                this.ratingCommentRepository.delete(ratingComment);
            }
        } else {
            throw new NoDataWithId("There is no rating and comment with this id!");
        }
    }

}
