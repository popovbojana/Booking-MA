package com.example.booking.controller;

import com.example.booking.service.interfaces.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }
}
