package com.example.booking_ma.DTO;


public class ReportRangeDTO {
    private String from;
    private String until;

    public ReportRangeDTO() {
    }

    public ReportRangeDTO(String from, String until) {
        this.from = from;
        this.until = until;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
}
