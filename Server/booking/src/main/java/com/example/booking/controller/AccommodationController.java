package com.example.booking.controller;

import com.example.booking.dto.*;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import com.example.booking.service.interfaces.IAccommodationChangeService;
import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api/accommodation/")
public class AccommodationController {

    private final IAccommodationService accommodationService;
    private final IAccommodationChangeService accommodationChangeService;

    public AccommodationController(IAccommodationService accommodationService, IAccommodationChangeService accommodationChangeService){
        this.accommodationService = accommodationService;
        this.accommodationChangeService = accommodationChangeService;
    }

    //owner
    @PostMapping(value = "add-new/{ownersId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewAccommodation(@PathVariable("ownersId") Long ownersId, @RequestBody NewAccommodationDTO newAccommodation){
        this.accommodationService.addNewAccommodation(ownersId, newAccommodation);
        return new ResponseEntity<>("Successfully saved new accommodation! Waiting for admin to approve.", HttpStatus.OK);
    }

    //owner
    @GetMapping(value = "all-accommodation/{ownersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAccommodationForOwner(@PathVariable("ownersId") Long ownersId){
        List<Accommodation> accommodations = this.accommodationService.getAllAccommodationsForOwner(ownersId);
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    //owner
    @PostMapping(value = "change-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeAccommodation(@PathVariable("id") Long id, @RequestBody AccommodationChangesDTO changes){
        this.accommodationChangeService.addAccommodationChange(id, changes);
        return new ResponseEntity<>("Accommodation changes saved! Waiting for admin to approve them.", HttpStatus.OK);
    }

    //admin, guest
    @GetMapping(value = "all-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getallAccommodation(){
        List<Accommodation> accommodations = this.accommodationService.getAll();
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    //admin
    @GetMapping(value = "all-new-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNewAccommodation(){
        List<Accommodation> accommodations = this.accommodationService.getAllNew();
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    //admin
    @GetMapping(value = "all-changes-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllChangesAccommodation(){
        List<AccommodationChange> changes = this.accommodationChangeService.getAll();
        List<AccommodationChangeDisplayDTO> changesDisplay = new ArrayList<>();
        for (AccommodationChange a : changes){
            changesDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(changesDisplay, HttpStatus.OK);
    }

    //admin
    @PutMapping(value = "approval-new-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> approveNewAccommodation(@PathVariable("id") Long id, @RequestBody ApprovalDTO approvalDTO){
        if(approvalDTO.isApproval() && this.accommodationService.approveNewAccommodation(id, true)){
            return new ResponseEntity<>("Approved accommodation!", HttpStatus.OK);
        }
        if(!approvalDTO.isApproval() && this.accommodationService.approveNewAccommodation(id, false)){
            return new ResponseEntity<>("Disapproved accommodation!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Accommodation with this id does not exist.", HttpStatus.OK);
    }

    //admin
    @PutMapping(value = "approval-changes-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> approveChangesAccommodation(@PathVariable("id") Long id, @RequestBody ApprovalDTO approvalDTO){
        if(approvalDTO.isApproval() && this.accommodationService.approveAccommodationChanges(id, true)){
            return new ResponseEntity<>("Approved changes for accommodation!", HttpStatus.OK);
        }
        if(!approvalDTO.isApproval() && this.accommodationService.approveAccommodationChanges(id, false)){
            return new ResponseEntity<>("Disapproved changes for accommodation!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Accommodation with this id does not exist.", HttpStatus.OK);
    }
}
