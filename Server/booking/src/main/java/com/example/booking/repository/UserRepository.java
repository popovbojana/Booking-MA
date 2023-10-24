package com.example.booking.repository;

import com.example.booking.model.User;
import com.example.booking.model.enums.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findById(Long id);
    public List<User> findUsersByEmail(String email);
    public Optional<User> findByEmailAndRole(String Email, Role role);

}
