package com.example.booking.service.interfaces;

import com.example.booking.dto.LoginDTO;
import com.example.booking.dto.NewUserDTO;
import com.example.booking.dto.TokenDTO;
import com.example.booking.exceptions.NotActivatedException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IUserService extends UserDetailsService {

    public boolean addNewUser(NewUserDTO newUserDTO) throws MessagingException, UnsupportedEncodingException;
    public boolean activateUser(Long userId) throws MessagingException, UnsupportedEncodingException;

    TokenDTO loginUser(LoginDTO login) throws NotActivatedException;
}
