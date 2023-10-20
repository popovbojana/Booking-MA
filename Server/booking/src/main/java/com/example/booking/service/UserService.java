package com.example.booking.service;

import com.example.booking.dto.NewUserDTO;
import com.example.booking.model.User;
import com.example.booking.repository.UserRepository;
import com.example.booking.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void addNewUser(NewUserDTO newUserDTO) {
        //TODO: dodati provere da li postoji korisnik sa emailom
        User user = new User(newUserDTO.getEmail(), newUserDTO.getPassword(), newUserDTO.getName(), newUserDTO.getSurname(), newUserDTO.getAddress(), newUserDTO.getPhoneNumber(), newUserDTO.getRole());
        this.userRepository.save(user);
    }

}
