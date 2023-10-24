package com.example.booking.controller;

import com.example.booking.dto.NewUserDTO;
import com.example.booking.service.interfaces.IUserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewUser(@RequestBody NewUserDTO newUser) throws MessagingException, UnsupportedEncodingException {
        if(this.userService.addNewUser(newUser)){
            return new ResponseEntity<>("Successfully registered!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessful registration! Account with this email already exists.", HttpStatus.OK);
    }

    @GetMapping(value = "activation/{userId}")
    public ResponseEntity<?> activateUserAccount(@PathVariable("userId") Long userId) throws MessagingException, UnsupportedEncodingException {
        if(this.userService.activateUser(userId)){
            return new ResponseEntity<>("Successfully activated account!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessful activation! Activation link has expired (you will get new one now) or account already activated.", HttpStatus.OK);
    }
}
