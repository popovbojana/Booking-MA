package com.example.booking.controller;

import com.example.booking.dto.NewUserDTO;
import com.example.booking.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewUser(@RequestBody NewUserDTO newUser){
        if(this.userService.addNewUser(newUser)){
            return new ResponseEntity<>("Successfully registered!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessful registration! Account with this email already exists.", HttpStatus.OK);
    }
}
