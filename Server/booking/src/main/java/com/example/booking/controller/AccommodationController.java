package com.example.booking.controller;

import com.example.booking.service.interfaces.IAccommodationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodation/")
public class AccommodationController {

    private IAccommodationService accommodationService;

    public AccommodationController(IAccommodationService accommodationService){
        this.accommodationService = accommodationService;
    }
}
