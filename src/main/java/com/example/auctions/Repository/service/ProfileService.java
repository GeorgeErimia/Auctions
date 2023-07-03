package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.ProfileNotFoundException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Model.Profile;
import com.example.auctions.Model.User;
import com.example.auctions.Model.dtos.ProfileDTO;
import com.example.auctions.Model.dtos.request.ProfileUpdateRequestDTO;
import com.example.auctions.Repository.ProfileRepository;
import com.example.auctions.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();


    public List<ProfileDTO> getAllProfiles() throws EmptyListException {
        List<Profile> profiles = profileRepository.findAll();
        if(profiles.isEmpty()){
            throw new EmptyListException("There are no profiles!");
        }
        else {
            return profiles.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public ProfileDTO getProfileById(Long id) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if(!profile.isPresent()){
            throw new ProfileNotFoundException("Profile with id '" + id + "' not found!");
        }
        else {
            return toDTO(profile.get());
        }
    }

    public ProfileDTO getProfileByUsername(String username) throws UserNotFoundException, ProfileNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UserNotFoundException("User '" + username + "' not found!");
        }
        else {
            if(user.get().getProfile() == null){
                throw new ProfileNotFoundException("Profile for user '" + username + "' not found!");
            }
            else {
                return toDTO(user.get().getProfile());
            }
        }
    }

    public List<ProfileDTO> getProfilesByFirstName(String firstName) throws EmptyListException {
        List<Profile> profiles = profileRepository.findByFirstName(firstName);
        if(profiles.isEmpty()){
            throw new EmptyListException("There are no profiles with first name '" + firstName + "'!");
        }
        else {
            return profiles.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public List<ProfileDTO> getProfilesByLastName(String lastName) throws EmptyListException {
        List<Profile> profiles = profileRepository.findByLastName(lastName);
        if(profiles.isEmpty()){
            throw new EmptyListException("There are no profiles with last name '" + lastName + "'!");
        }
        else {
            return profiles.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public List<ProfileDTO> getProfilesByImageUrl(String imageUrl) throws EmptyListException {
        List<Profile> profiles = profileRepository.findByProfilePictureUrl(imageUrl);
        if(profiles.isEmpty()){
            throw new EmptyListException("There are no profiles with image url '" + imageUrl + "'!");
        }
        else {
            return profiles.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public ProfileDTO updateProfileByUsername(ProfileUpdateRequestDTO requestDTO, String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UserNotFoundException("User '" + username + "' not found!");
        }
        else{
            Profile profile = user.get().getProfile();
            if(profile == null){
                profile = new Profile();
            }
            if(requestDTO.getFirstName() != null){
                profile.setFirstName(requestDTO.getFirstName());
            }
            if(requestDTO.getLastName() != null){
                profile.setLastName(requestDTO.getLastName());
            }
            if(requestDTO.getDescription() != null){
                profile.setDescription(requestDTO.getDescription());
            }
            if(requestDTO.getProfilePictureUrl() != null){
                profile.setProfilePictureUrl(requestDTO.getProfilePictureUrl());
            }
            User updatedUser = user.get();
            updatedUser.setProfile(profile);
            userRepository.save(updatedUser);
            return toDTO(profile);
        }
    }

    public ProfileDTO updateProfileById(ProfileUpdateRequestDTO requestDTO, Long id) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if(!profile.isPresent()){
            throw new ProfileNotFoundException("Profile with id '" + id + "' not found!");
        }
        else{
            if(requestDTO.getFirstName() != null){
                profile.get().setFirstName(requestDTO.getFirstName());
            }
            if(requestDTO.getLastName() != null){
                profile.get().setLastName(requestDTO.getLastName());
            }
            if(requestDTO.getDescription() != null){
                profile.get().setDescription(requestDTO.getDescription());
            }
            if(requestDTO.getProfilePictureUrl() != null){
                profile.get().setProfilePictureUrl(requestDTO.getProfilePictureUrl());
            }
            profileRepository.save(profile.get());
            return toDTO(profile.get());
        }
    }

    private void deleteProfileById(Long id) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if(!profile.isPresent()){
            throw new ProfileNotFoundException("Profile with id '" + id + "' not found!");
        }
        else {
            profileRepository.delete(profile.get());
        }
    }

    private Profile toEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }

    private ProfileDTO toDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }
}
