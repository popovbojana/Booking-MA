package com.example.booking.service.interfaces;

import com.example.booking.dto.*;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.model.Accommodation;
import com.example.booking.model.AvailabilityPrice;

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

    List<AvailabilityDisplayDTO> getAvailabilitiesPricesByAccommodatioId(Long accommodationId) throws NoDataWithId;

    void handleAutoApprove(Long accommodationId) throws NoDataWithId;
}
