package com.example.booking_ma.model;

import java.io.Serializable;

public class Guest extends User {

    private String name;
    private String surname;

    public Guest() {
    }

    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
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
}
