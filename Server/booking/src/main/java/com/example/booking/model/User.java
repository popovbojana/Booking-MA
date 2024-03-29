package com.example.booking.model;

import com.example.booking.dto.AccommodationDisplayDTO;
import com.example.booking.dto.AvailabilityDisplayDTO;
import com.example.booking.dto.UserDisplayDTO;
import com.example.booking.dto.UserUpdateDTO;
import com.example.booking.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

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

    private boolean activated;

    private LocalDateTime activationLinkSent;

    private boolean reported;

    private String reportedReason;

    private boolean blocked;

    private int passwordCharNumber;

    public User(String email, String password, String name, String surname, String address, String phoneNumber, Role role, int passwordCharNumber){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.activated = false;
        this.activationLinkSent = LocalDateTime.now();
        this.reported = false;
        this.reportedReason = null;
        this.blocked = false;
        this.passwordCharNumber = passwordCharNumber;
    }


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRole().toString()));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDisplayDTO parseToDisplay() {
        return new UserDisplayDTO(id, email, password, name, surname, address, phoneNumber, role, activated, activationLinkSent, reported, reportedReason, blocked, passwordCharNumber);
    }
}
