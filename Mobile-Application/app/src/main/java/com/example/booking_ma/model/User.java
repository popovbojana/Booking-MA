package com.example.booking_ma.model;

import androidx.navigation.NavType;

import com.example.booking_ma.model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

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

    public User() {
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(Long id, String email, String password, String name, String surname, String address, String phoneNumber, Role role, boolean activated, LocalDateTime activationLinkSent, boolean reported, String reportedReason, boolean blocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.activated = activated;
        this.activationLinkSent = activationLinkSent;
        this.reported = reported;
        this.reportedReason = reportedReason;
        this.blocked = blocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public LocalDateTime getActivationLinkSent() {
        return activationLinkSent;
    }

    public void setActivationLinkSent(LocalDateTime activationLinkSent) {
        this.activationLinkSent = activationLinkSent;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public String getReportedReason() {
        return reportedReason;
    }

    public void setReportedReason(String reportedReason) {
        this.reportedReason = reportedReason;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
