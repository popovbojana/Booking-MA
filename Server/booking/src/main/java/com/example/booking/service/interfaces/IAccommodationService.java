package com.example.booking.service.interfaces;

import com.example.booking.dto.NewAccommodationDTO;
import com.example.booking.model.Accommodation;

import java.util.List;

public interface IAccommodationService {

    public void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO);
    public List<Accommodation> getAllAccommodationsForOwner(Long ownersId);
    public List<Accommodation> getAllNew();
    public List<Accommodation> getAllChanges();
    public boolean approveNewAccommodation(Long id, boolean approval);
    public boolean approveAccommodationChanges(Long id, boolean approval);
    public List<Accommodation> getAll();
}
