package com.example.booking.controller;

import com.example.booking.dto.*;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.PasswordNotMatchingException;
import com.example.booking.model.Accommodation;
import com.example.booking.model.Guest;
import com.example.booking.model.Owner;
import com.example.booking.model.User;
import com.example.booking.model.enums.Role;
import com.example.booking.service.interfaces.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(IUserService userService, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewUser(@RequestBody NewUserDTO newUser) throws MessagingException, UnsupportedEncodingException {
        if (this.userService.addNewUser(newUser)) {
            return new ResponseEntity<>(new MessageDTO("Successfully registered!"), HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessful registration! Account with this email already exists.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "activation/{userId}")
    public ResponseEntity<?> activateUserAccount(@PathVariable("userId") Long userId) throws MessagingException, UnsupportedEncodingException {
        if (this.userService.activateUser(userId)) {
            return new ResponseEntity<>(new MessageDTO("Successfully activated account!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Unsuccessful activation! Activation link has expired (you will get new one now) or account already activated."), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        try {
            return new ResponseEntity<>(this.userService.loginUser(login), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "report-guest/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> reportGuest(@PathVariable("id") Long id, @RequestBody ReportedUserReasonDTO reason) {
        try {
            this.userService.reportGuest(id, reason);
            return new ResponseEntity<>(new MessageDTO("Successfully reported guest!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "report-owner/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<?> reportOwner(@PathVariable("id") Long id, @RequestBody ReportedUserReasonDTO reason) {
        try {
            this.userService.reportOwner(id, reason);
            return new ResponseEntity<>(new MessageDTO("Successfully reported owner!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "all-users", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = this.userService.getAll();
        List<UserDisplayDTO> userDisplay = new ArrayList<>();
        for (User u : users) {
            userDisplay.add(u.parseToDisplay());
        }
        return new ResponseEntity<>(userDisplay, HttpStatus.OK);
    }

    @PutMapping(value = "update-user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO userUpdate) {
        try {
            this.userService.update(userId, userUpdate);
            return new ResponseEntity<>(new MessageDTO("Successfully updated user!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/change-password/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> changePassword(@PathVariable("userId") Long userId, @RequestBody ChangePasswordDTO changePassword) {
        try {
            User user = userService.getUser(userId).orElseThrow(() -> new NoDataWithId("User not found"));

            if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                throw new PasswordNotMatchingException("Incorrect old password");
            }

            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            user.setPasswordCharNumber(changePassword.getNewPassword().length());
            userService.add(user);

            return new ResponseEntity<>(new MessageDTO("Password successfully changed!"), HttpStatus.OK);
        } catch (PasswordNotMatchingException e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user-display/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> getUserDisplay(@PathVariable("userId") Long userId) {
        try {
            User user = userService.getUser(userId).orElseThrow(() -> new NoDataWithId("User not found"));
            UserDisplayDTO userDisplay = user.parseToDisplay();

            return new ResponseEntity<>(userDisplay, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/check-password/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> checkUserPassword(@PathVariable("userId") Long userId, @RequestBody UserPasswordDTO userPassword) {
        try {
            User user = userService.getUser(userId).orElseThrow(() -> new NoDataWithId("User not found"));

            if (passwordEncoder.matches(userPassword.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.getUser(userId)
                    .orElseThrow(() -> new NoDataWithId("User not found"));
            userService.removeUser(userId);

            return new ResponseEntity<>(new MessageDTO("User deleted successfully"), HttpStatus.OK);

        } catch (NoDataWithId e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "guest/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('GUEST','OWNER','ADMIN')")
    public ResponseEntity<?> getGuest(@PathVariable("guestId") Long guestId) {
        Guest guest = this.userService.getGuest(guestId).get();
        return new ResponseEntity<>(guest.parseToDisplayGuest(), HttpStatus.OK);
    }

//    public
}
