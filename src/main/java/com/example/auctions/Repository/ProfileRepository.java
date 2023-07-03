package com.example.auctions.Repository;

import com.example.auctions.Model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByFirstName(String firstName);
    List<Profile> findByLastName(String lastName);
    List<Profile> findByProfilePictureUrl(String profilePictureUrl);
}
