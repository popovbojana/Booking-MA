package com.example.booking.controller;

import com.example.booking.dto.NewAccommodationDTO;
import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/accommodation/")
public class AccommodationController {

    private final IAccommodationService accommodationService;

    public AccommodationController(IAccommodationService accommodationService){
        this.accommodationService = accommodationService;
    }

    @PostMapping(value = "add-new/{ownersId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewAccommodation(@PathVariable("ownersId") Long ownersId, @RequestBody NewAccommodationDTO newAccommodation){
        this.accommodationService.addNewAccommodation(ownersId, newAccommodation);
        return new ResponseEntity<>("Successfully added new accommodation!", HttpStatus.OK);
    }
}
