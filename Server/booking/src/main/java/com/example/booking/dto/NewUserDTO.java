package com.example.booking.dto;

import com.example.booking.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private Role role;

}
