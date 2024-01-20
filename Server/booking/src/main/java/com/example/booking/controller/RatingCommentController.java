package com.example.booking.controller;

import com.example.booking.dto.ApprovalDTO;
import com.example.booking.dto.MessageDTO;
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
            return new ResponseEntity<>(new MessageDTO("Successfully added new rating and comment!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> deleteRatingComment(@PathVariable("id") Long id){
        try {
            this.ratingCommentService.deleteRatingComment(id);
            return new ResponseEntity<>(new MessageDTO("Successfully deleted rating and comment!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-for-guest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GUEST', 'ADMIN')")
    public ResponseEntity<?> getAllForGuest(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllForGuest(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-for-owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('GUEST', 'OWNER', 'ADMIN')")
    public ResponseEntity<?> getAllForOwner(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllForOwner(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
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

    @GetMapping(value = "all-unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllUnapproved(){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getAllUnapproved(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "approve/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> approve(@PathVariable("id") Long id, @RequestBody ApprovalDTO approval){
        try {
            if(approval.isApproval()){
                this.ratingCommentService.approve(id, true);
                return new ResponseEntity<>(new MessageDTO("Successfully approved rating and comment!"), HttpStatus.OK);
            } else {
                this.ratingCommentService.approve(id, false);
                return new ResponseEntity<>(new MessageDTO("Successfully disapproved rating and comment!"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "report/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> report(@PathVariable("id") Long id){
        try {
            this.ratingCommentService.report(id);
            return new ResponseEntity<>(new MessageDTO("Successfully reported rating and comment!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-reported-comments/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getReportedComments(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(this.ratingCommentService.getReportedComments(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "delete-reported-comments/{ratingCommentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> handleReportedComment(@PathVariable("id") Long ratingCommentId, @RequestBody ApprovalDTO approvalDTO){
        try {
            this.ratingCommentService.handleReportedComment(ratingCommentId, approvalDTO);
            return new ResponseEntity<>(new MessageDTO("Rating comment report is successfully deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
