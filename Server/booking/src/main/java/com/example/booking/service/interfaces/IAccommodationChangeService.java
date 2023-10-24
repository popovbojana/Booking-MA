package com.example.booking.service.interfaces;

import com.example.booking.dto.AccommodationChangesDTO;
import com.example.booking.model.AccommodationChange;

import java.util.List;

public interface IAccommodationChangeService {
    void addAccommodationChange(Long id, AccommodationChangesDTO changes);

    List<AccommodationChange> getAll();
}
