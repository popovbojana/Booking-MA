package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportAllAccommodationsDTO {
    private HashMap<String, Integer> reservations;
    private HashMap<String, Float> profit;
}
