package com.example.booking.service.interfaces;

import com.example.booking.dto.NewAccommodationDTO;

public interface IAccommodationService {

    public void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO);

}
