package com.example.auctions.Repository;

import com.example.auctions.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<List<User>> findAllByProfileFirstNameContaining(String firstName);

    Optional<List<User>> findAllByProfileLastNameContaining(String lastName);
    Boolean existsByUsername(String username);

    public void deleteByUsername(String username);
}
