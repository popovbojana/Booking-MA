package com.example.booking.service;

import com.example.booking.dto.AllRatingsDisplay;
import com.example.booking.dto.RateCommentDTO;
import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.model.Accommodation;
import com.example.booking.model.Guest;
import com.example.booking.model.Owner;
import com.example.booking.model.RatingComment;
import com.example.booking.model.enums.RatingCommentType;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.*;
import com.example.booking.service.interfaces.IRatingCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingCommentService implements IRatingCommentService {

    private final RatingCommentRepository ratingCommentRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public RatingCommentService(RatingCommentRepository ratingCommentRepository, UserRepository userRepository, AccommodationRepository accommodationRepository) {
        this.ratingCommentRepository = ratingCommentRepository;
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public void rateComment(RateCommentDTO rateCommentDTO) throws NoDataWithId {
        if (this.userRepository.findById(rateCommentDTO.getGuestsId()).isPresent() && this.userRepository.findById(rateCommentDTO.getGuestsId()).get().getRole() == Role.GUEST){
            Guest guest = (Guest) this.userRepository.findById(rateCommentDTO.getGuestsId()).get();
            if (rateCommentDTO.getType() == RatingCommentType.FOR_OWNER) {
                if (this.userRepository.findById(rateCommentDTO.getOwnersId()).isPresent() && this.userRepository.findById(rateCommentDTO.getOwnersId()).get().getRole() == Role.OWNER){
                    //TODO: proveriti da li je imao bar jednu rezervaciju u proslosti koju nije otkazao
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
                    //TODO: proveriti da li je odseo bar jednom u proslosti i da nije proslo vise od 7 dana od toga
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
            return new AllRatingsDisplay(total/all.size(), allForDisplay);
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
            return new AllRatingsDisplay(total/all.size(), allForDisplay);
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

}
