package com.example.booking.repository;

import com.example.booking.model.Accommodation;
import com.example.booking.model.Guest;
import com.example.booking.model.User;
import com.example.booking.model.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findAll();
    public Optional<User> findById(Long id);
    public List<User> findUsersByEmail(String email);
    public Optional<User> findByEmailAndRole(String Email, Role role);
    public Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id = :id and u.role = 'GUEST'")
    Guest findGuestById(@Param("id") Long id);
}
