package com.example.booking.service.interfaces;

import com.example.booking.dto.*;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.NotActivatedException;
import com.example.booking.model.Guest;
import com.example.booking.model.Owner;
import com.example.booking.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {

    void removeUser(Long id);

    void removeOwner(Long id);

    Optional<User> getUser(Long id);

    Optional<Guest> getGuest(Long id);

    Optional<Owner> getOwner(Long id);

    void add(User user);

    public List<User> getAll();

    public boolean addNewUser(NewUserDTO newUserDTO) throws MessagingException, UnsupportedEncodingException;
    public boolean activateUser(Long userId) throws MessagingException, UnsupportedEncodingException;

    TokenDTO loginUser(LoginDTO login) throws NotActivatedException, UsernameNotFoundException;

    void reportGuest(Long id, ReportedUserReasonDTO reason) throws NoDataWithId;

    void reportOwner(Long id, ReportedUserReasonDTO reason) throws NoDataWithId;

    void update(Long userId, UserUpdateDTO userUpdate) throws NoDataWithId;

    String convertPasswordToStars(String password);
}
