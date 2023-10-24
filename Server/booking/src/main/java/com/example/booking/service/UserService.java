package com.example.booking.service;

import com.example.booking.dto.NewUserDTO;
import com.example.booking.model.User;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.interfaces.IUserService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, MailService mailService){
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public boolean addNewUser(NewUserDTO newUserDTO) throws MessagingException, UnsupportedEncodingException {
        List<User> existingUsers = this.userRepository.findUsersByEmail(newUserDTO.getEmail());
        if(existingUsers.size() != 0){
            if(existingUsers.size() == 1){
                if((existingUsers.get(0).getRole() == Role.GUEST && newUserDTO.getRole() == Role.OWNER) || (existingUsers.get(0).getRole() == Role.OWNER && newUserDTO.getRole() == Role.GUEST)){
                    return addToRepository(newUserDTO);
                }
                return false;
            } else {
                return false;
            }
        }
        return addToRepository(newUserDTO);
    }

    private boolean addToRepository(NewUserDTO newUserDTO) throws MessagingException, UnsupportedEncodingException {
        User user = new User(newUserDTO.getEmail(), newUserDTO.getPassword(), newUserDTO.getName(), newUserDTO.getSurname(), newUserDTO.getAddress(), newUserDTO.getPhoneNumber(), newUserDTO.getRole());
        this.userRepository.save(user);
        this.mailService.sendActivationEmail(newUserDTO.getEmail(), this.userRepository.findByEmailAndRole(newUserDTO.getEmail(), newUserDTO.getRole()).get().getId());
        return true;
    }

    @Override
    public boolean activateUser(Long userId) throws MessagingException, UnsupportedEncodingException {
        if(this.userRepository.findById(userId).isPresent()){
            User user = this.userRepository.findById(userId).get();
            if(!user.isActivated()){
                if(LocalDateTime.now().isBefore(user.getActivationLinkSent().plusHours(24))){
                    user.setActivated(true);
                    this.userRepository.save(user);
                    return true;
                } else {
                    user.setActivationLinkSent(LocalDateTime.now());
                    this.userRepository.save(user);
                    this.mailService.sendActivationEmail(user.getEmail(), userId);
                }
            }
        }
        return false;
    }
}
