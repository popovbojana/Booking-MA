package com.example.booking.service.interfaces;

import com.example.booking.dto.NewUserDTO;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IUserService {

    public boolean addNewUser(NewUserDTO newUserDTO) throws MessagingException, UnsupportedEncodingException;
    public boolean activateUser(Long userId) throws MessagingException, UnsupportedEncodingException;

}
