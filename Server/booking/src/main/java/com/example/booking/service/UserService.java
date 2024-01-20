package com.example.booking.service;

import com.example.booking.dto.*;
import com.example.booking.exceptions.NoDataWithId;
import com.example.booking.exceptions.NotActivatedException;
import com.example.booking.exceptions.RequirementNotSatisfied;
import com.example.booking.model.*;
import com.example.booking.model.enums.ReservationState;
import com.example.booking.model.enums.Role;
import com.example.booking.repository.*;
import com.example.booking.security.TokenUtils;
import com.example.booking.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final AvailabilityPriceRepository availabilityPriceRepository;
    private final RatingCommentRepository ratingCommentRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, AccommodationRepository accommodationRepository, AvailabilityPriceRepository availabilityPriceRepository, RatingCommentRepository ratingCommentRepository, ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.availabilityPriceRepository = availabilityPriceRepository;
        this.ratingCommentRepository = ratingCommentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void removeUser(Long id){
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUser(Long id){
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<Guest> getGuest(Long id){
        return this.userRepository.findGuestById(id);
    }

    @Override
    public Optional<Owner> getOwner(Long id){
        return this.userRepository.findOwnerById(id);
    }

    @Override
    public void add(User user){
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAll(){
        return this.userRepository.findAll();
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
        User user = new User(newUserDTO.getEmail(), this.passwordEncoder.encode(newUserDTO.getPassword()), newUserDTO.getName(), newUserDTO.getSurname(), newUserDTO.getAddress(), newUserDTO.getPhoneNumber(), newUserDTO.getRole(), newUserDTO.getPassword().length());
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

    @Override
    public TokenDTO loginUser(LoginDTO login) throws NotActivatedException, UsernameNotFoundException {
        try{
            User user = loadUserByUsername(login.getEmail());

            if(!user.isActivated()){
                throw new NotActivatedException("Not activated account!");
            }

            TokenDTO token = new TokenDTO();
            token.setAccessToken(this.tokenUtils.generateToken(user));
            token.setRefreshToken(this.tokenUtils.generateRefreshToken(user));
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return token;
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", login.getEmail()));
        }

    }

    @Override
    public void reportGuest(Long id, ReportedUserReasonDTO reason) throws NoDataWithId {
        if (this.userRepository.findById(id).isPresent() && this.userRepository.findById(id).get().getRole() == Role.GUEST){
            User guest = this.userRepository.findById(id).get();
            guest.setReported(true);
            guest.setReportedReason(reason.getReason());
            this.userRepository.save(guest);
        } else {
            throw new NoDataWithId("There is no guest with this id!");
        }
    }

    @Override
    public void reportOwner(Long id, ReportedUserReasonDTO reason) throws NoDataWithId {
        if (this.userRepository.findById(id).isPresent() && this.userRepository.findById(id).get().getRole() == Role.OWNER){
            User owner = this.userRepository.findById(id).get();
            owner.setReported(true);
            owner.setReportedReason(reason.getReason());
            this.userRepository.save(owner);
        } else {
            throw new NoDataWithId("There is no owner with this id!");
        }
    }

    @Override
    public void update(Long userId, UserUpdateDTO userUpdate) throws NoDataWithId {
        if (this.userRepository.findById(userId).isPresent()){
            User user = this.userRepository.findById(userId).get();
            if(!userUpdate.getName().equals("")){
                user.setName(userUpdate.getName());
            }
            if(!userUpdate.getSurname().equals("")){
                user.setSurname(userUpdate.getSurname());
            }
            if(!userUpdate.getPhoneNumber().equals("")){
                user.setEmail(userUpdate.getPhoneNumber());
            }
            if(!userUpdate.getEmail().equals("")){
                user.setEmail(userUpdate.getEmail());
            }
            this.userRepository.save(user);
        } else {
            throw new NoDataWithId("There is no user with this id!");
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
        }
        return user;
    }

    @Override
    public String convertPasswordToStars(String password){
        int numberOfAsterisks = password.length();
        StringBuilder passwordStars = new StringBuilder();
        for (int i = 0; i < numberOfAsterisks; i++) {
            passwordStars.append("*");
        }
        return passwordStars.toString();
    }

    @Override
    public List<UserDisplayDTO> getReportedUsers(){
        List<User> users = this.userRepository.findAll();
        List<UserDisplayDTO> reportedUserDisplayDTOS = new ArrayList<>();
        if(!users.isEmpty()){
            for(User u : users){
                if(u.isReported()){
                    reportedUserDisplayDTOS.add(u.parseToDisplay());
                }
            }
        }
        return reportedUserDisplayDTOS;
    }

    @Override
    public void handleReportedUser(Long userId, ApprovalDTO approval) throws NoDataWithId, RequirementNotSatisfied {
        if(!this.userRepository.findById(userId).isPresent()){
            throw new NoDataWithId("There is no user with this id!");
        }
        User user = this.userRepository.findById(userId).get();
        if(!user.isReported()){
            throw new RequirementNotSatisfied("Cant delete unreported comment");
        }
        if(user.getRole() == Role.GUEST) {
            if (approval.isApproval()) {
                user.setBlocked(true);
                List<Reservation> pendingReservations = this.reservationRepository.findAllPendingByGuestId(userId);
                for (Reservation r : pendingReservations) {
                    r.setReservationState(ReservationState.CANCELED);
                    this.reservationRepository.save(r);
                }
            } else {
                user.setBlocked(false);
            }
        }
    }
}
