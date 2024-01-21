package com.example.booking_ma.DTO;

import java.util.HashMap;

public class ReportAllAccommodationsDTO {
    private HashMap<String, Integer> reservations;
    private HashMap<String, Float> profit;

    public ReportAllAccommodationsDTO() {
    }

    public ReportAllAccommodationsDTO(HashMap<String, Integer> reservations, HashMap<String, Float> profit) {
        this.reservations = reservations;
        this.profit = profit;
    }

    public HashMap<String, Integer> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Integer> reservations) {
        this.reservations = reservations;
    }

    public HashMap<String, Float> getProfit() {
        return profit;
    }

    public void setProfit(HashMap<String, Float> profit) {
        this.profit = profit;
    }
}
