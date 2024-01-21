package com.example.booking.service.interfaces;

import com.example.booking.dto.AccommodationDisplayDTO;
import com.example.booking.dto.NewAccommodationDTO;
import com.example.booking.dto.FavoriteAccommodationDTO;
import com.example.booking.dto.ReportAccommodationDTO;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.model.Accommodation;

import java.util.List;

public interface IAccommodationService {

    List<Accommodation> GetAll();

    void addNewAccommodation(Long ownersId, NewAccommodationDTO newAccommodationDTO);
    List<Accommodation> getAllAccommodationsForOwner(Long ownersId);
    List<Accommodation> getAllNew();
    List<Accommodation> getAllChanges();
    boolean approveNewAccommodation(Long id, boolean approval);
    boolean approveAccommodationChanges(Long id, boolean approval);
    List<Accommodation> getAll();
    void addToFavorites(FavoriteAccommodationDTO favAccommodation) throws NoDataWithId;
    void removeFromFavorites(FavoriteAccommodationDTO favAccommodation) throws NoDataWithId;
    List<AccommodationDisplayDTO> getAllFavoritesForGuest(Long id) throws NoDataWithId;
    AccommodationDisplayDTO getAccommodationById(Long id) throws NoDataWithId;

}
