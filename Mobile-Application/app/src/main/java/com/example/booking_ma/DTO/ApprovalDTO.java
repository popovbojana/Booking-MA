package com.example.booking_ma.DTO;

public class ApprovalDTO {

    private boolean approval;

    public ApprovalDTO() {
    }

    public ApprovalDTO(boolean approval) {
        this.approval = approval;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }
}
