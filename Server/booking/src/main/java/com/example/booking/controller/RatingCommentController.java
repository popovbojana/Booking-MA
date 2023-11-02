package com.example.booking.controller;

import com.example.booking.dto.RateCommentDTO;
import com.example.booking.service.interfaces.IAccommodationService;
import com.example.booking.service.interfaces.IRatingCommentService;
import com.example.booking.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating-comment/")
public class RatingCommentController {

    private final IRatingCommentService ratingCommentService;
    private final IUserService userService;
    private final IAccommodationService accommodationService;

    public RatingCommentController(IRatingCommentService ratingCommentService, IUserService userService, IAccommodationService accommodationService){
        this.ratingCommentService = ratingCommentService;
        this.userService = userService;
        this.accommodationService = accommodationService;
    }


    @PostMapping(value = "add-new", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> rateComment(@RequestBody RateCommentDTO rateCommentDTO){
        try{
            this.ratingCommentService.rateComment(rateCommentDTO);
            return new ResponseEntity<>("Successfully added new rating and comment!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> deleteRatingComment(@PathVariable("id") Long id){
        try {
            this.ratingCommentService.deleteRatingComment(id);
            return new ResponseEntity<>("Successfully deleted rating and comment!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-for-guest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GUEST', 'ADMIN')")
    public ResponseEntity<?> getAllForGuest(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllForGuest(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-for-owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GUEST', 'OWNER', 'ADMIN')")
    public ResponseEntity<?> getAllForOwner(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllForOwner(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-for-accommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GUEST', 'OWNER', 'ADMIN')")
    public ResponseEntity<?> getAllForAccommodation(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllForAccommodation(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
