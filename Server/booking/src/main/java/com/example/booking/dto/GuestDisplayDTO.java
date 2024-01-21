package com.example.booking.dto;

import com.example.booking.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDisplayDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private Role role;
    private boolean activated;
    private LocalDateTime activationLinkSent;
    private boolean reported;
    private String reportedReason;
    private boolean blocked;
    private int passwordCharNumber;
    private int cancelationsNumber;
}
