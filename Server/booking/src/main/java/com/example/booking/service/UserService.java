package com.example.booking.service;

import com.example.booking.dto.NewUserDTO;
import com.example.booking.model.User;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public boolean addNewUser(NewUserDTO newUserDTO) {
        //TODO: dodati provere da li postoji korisnik sa emailom
        List<User> existingUsers = this.userRepository.findUsersByEmail(newUserDTO.getEmail());
        if(existingUsers.size() != 0){
            if(existingUsers.size() == 1){
                if((existingUsers.get(0).getRole() == Role.GUEST && newUserDTO.getRole() == Role.OWNER) || (existingUsers.get(0).getRole() == Role.OWNER && newUserDTO.getRole() == Role.GUEST)){
                    User user = new User(newUserDTO.getEmail(), newUserDTO.getPassword(), newUserDTO.getName(), newUserDTO.getSurname(), newUserDTO.getAddress(), newUserDTO.getPhoneNumber(), newUserDTO.getRole());
                    this.userRepository.save(user);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        User user = new User(newUserDTO.getEmail(), newUserDTO.getPassword(), newUserDTO.getName(), newUserDTO.getSurname(), newUserDTO.getAddress(), newUserDTO.getPhoneNumber(), newUserDTO.getRole());
        this.userRepository.save(user);
        return true;
    }

}
