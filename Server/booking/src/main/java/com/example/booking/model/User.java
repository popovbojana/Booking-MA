package com.example.booking.model;

import com.example.booking.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String address;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, String password, String name, String surname, String address, String phoneNumber, String role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        if(role.equals("Guest")){
            this.role = Role.GUEST;
        }
        if(role.equals("Owner")){
            this.role = Role.OWNER;
        }
    }

}
