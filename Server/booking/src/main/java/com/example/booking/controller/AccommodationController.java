package com.example.booking.controller;

import com.example.booking.dto.*;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AccommodationChange;
import com.example.booking.service.interfaces.IAccommodationChangeService;
import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "add-new/{ownersId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> addNewAccommodation(@PathVariable("ownersId") Long ownersId, @RequestBody NewAccommodationDTO newAccommodation){
        this.accommodationService.addNewAccommodation(ownersId, newAccommodation);
        return new ResponseEntity<>(new MessageDTO("Successfully saved new accommodation! Waiting for admin to approve."), HttpStatus.OK);
    }

    @GetMapping(value = "all-accommodation/{ownersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> getAllAccommodationForOwner(@PathVariable("ownersId") Long ownersId){
        List<Accommodation> accommodations = this.accommodationService.getAllAccommodationsForOwner(ownersId);
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    @PostMapping(value = "change-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> changeAccommodation(@PathVariable("id") Long id, @RequestBody AccommodationChangesDTO changes){
        this.accommodationChangeService.addAccommodationChange(id, changes);
        return new ResponseEntity<>(new MessageDTO("Accommodation changes saved! Waiting for admin to approve them."), HttpStatus.OK);
    }


    @GetMapping(value = "all-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GUEST', 'OWNER')")
    public ResponseEntity<?> getAllAccommodation(){
        List<Accommodation> accommodations = this.accommodationService.getAll();
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    @GetMapping(value = "all-new-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllNewAccommodation(){
        List<Accommodation> accommodations = this.accommodationService.getAllNew();
        List<AccommodationDisplayDTO> accommodationDisplay = new ArrayList<>();
        for (Accommodation a : accommodations){
            accommodationDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(accommodationDisplay, HttpStatus.OK);
    }

    @GetMapping(value = "all-changes-accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllChangesAccommodation(){
        List<AccommodationChange> changes = this.accommodationChangeService.getAll();
        List<AccommodationChangeDisplayDTO> changesDisplay = new ArrayList<>();
        for (AccommodationChange a : changes){
            changesDisplay.add(a.parseToDisplay());
        }
        return new ResponseEntity<>(changesDisplay, HttpStatus.OK);
    }

    @PutMapping(value = "approval-new-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> approveNewAccommodation(@PathVariable("id") Long id, @RequestBody ApprovalDTO approvalDTO){
        if(approvalDTO.isApproval() && this.accommodationService.approveNewAccommodation(id, true)){
            return new ResponseEntity<>(new MessageDTO("Approved accommodation!"), HttpStatus.OK);
        }
        if(!approvalDTO.isApproval() && this.accommodationService.approveNewAccommodation(id, false)){
            return new ResponseEntity<>(new MessageDTO("Disapproved accommodation!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Accommodation with this id does not exist."), HttpStatus.OK);
    }

    @PutMapping(value = "approval-changes-accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> approveChangesAccommodation(@PathVariable("id") Long id, @RequestBody ApprovalDTO approvalDTO){
        if(approvalDTO.isApproval() && this.accommodationService.approveAccommodationChanges(id, true)){
            return new ResponseEntity<>(new MessageDTO("Approved changes for accommodation!"), HttpStatus.OK);
        }
        if(!approvalDTO.isApproval() && this.accommodationService.approveAccommodationChanges(id, false)){
            return new ResponseEntity<>(new MessageDTO("Disapproved changes for accommodation!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Accommodation with this id does not exist."), HttpStatus.OK);
    }

    @PutMapping(value = "add-to-favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> addToFavorites(@RequestBody FavoriteAccommodationDTO favAccommodation){
        try{
            this.accommodationService.addToFavorites(favAccommodation);
            return new ResponseEntity<>(new MessageDTO("Successfully added accommodation to your favorites!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "remove-from-favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> removeFromFavorites(@RequestBody FavoriteAccommodationDTO favAccommodation){
        try{
            this.accommodationService.removeFromFavorites(favAccommodation);
            return new ResponseEntity<>(new MessageDTO("Successfully removed accommodation to your favorites!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-favorites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> getAllFavoritesForGuest(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(this.accommodationService.getAllFavoritesForGuest(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "accommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN')")
    public ResponseEntity<?> getAccommodationById(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(this.accommodationService.getAccommodationById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
