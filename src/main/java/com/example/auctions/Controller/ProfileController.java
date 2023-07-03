package com.example.auctions.Controller;

import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.ProfileNotFoundException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Model.dtos.ProfileDTO;
import com.example.auctions.Model.dtos.request.ProfileUpdateRequestDTO;
import com.example.auctions.Repository.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() throws EmptyListException {
        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileDTO> getProfileByUsername(@PathVariable String username)
    throws UserNotFoundException, ProfileNotFoundException {
        return new ResponseEntity<>(profileService.getProfileByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id)
        throws ProfileNotFoundException {
        return new ResponseEntity<>(profileService.getProfileById(id), HttpStatus.OK);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<ProfileDTO>> getProfileByFirstName(@PathVariable String firstName)
        throws EmptyListException {
        return new ResponseEntity<>(profileService.getProfilesByFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<ProfileDTO>> getProfileByLastName(@PathVariable String lastName)
        throws EmptyListException {
        return new ResponseEntity<>(profileService.getProfilesByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/image/{profilePictureUrl}")
    public ResponseEntity<List<ProfileDTO>> getProfileByProfilePictureUrl(@PathVariable String profilePictureUrl)
        throws EmptyListException {
        return new ResponseEntity<>(profileService.getProfilesByImageUrl(profilePictureUrl), HttpStatus.OK);
    }

    @PutMapping("/username/{username}/update")
    public ResponseEntity<ProfileDTO> updateProfileByUsername(@PathVariable String username,
                                                    @RequestBody ProfileUpdateRequestDTO requestDTO)
        throws UserNotFoundException {
        return new ResponseEntity<>(profileService.updateProfileByUsername(requestDTO, username), HttpStatus.OK);
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<ProfileDTO> updateProfileById(@PathVariable Long id,
                                                    @RequestBody ProfileUpdateRequestDTO requestDTO)
        throws ProfileNotFoundException {
        return new ResponseEntity<>(profileService.updateProfileById(requestDTO, id), HttpStatus.OK);
    }

}
