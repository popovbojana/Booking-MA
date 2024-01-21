package com.example.booking_ma.DTO;

public class ReportedUserReasonDTO {
    private String reason;

    public ReportedUserReasonDTO() {
    }

    public ReportedUserReasonDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
