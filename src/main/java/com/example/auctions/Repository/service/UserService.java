package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.UpdateFailedException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Exceptions.UsernameExistsException;
import com.example.auctions.Model.Profile;
import com.example.auctions.Model.User;
import com.example.auctions.Model.dtos.request.UserCreateRequestDTO;
import com.example.auctions.Model.dtos.UserDTO;
import com.example.auctions.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public List<UserDTO> getAllUsers() throws EmptyListException {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new EmptyListException();
        }
        else {
            return users.stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with id: " + id  + " not found!");
        }
        else {
            return toDTO(user.get());
        }
    }

    public UserDTO getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with username: " + username + " not found!");
        }
        else {
            return toDTO(user.get());
        }
    }

    public List<UserDTO> getAllUsersByFirstName(String firstName) throws EmptyListException {
        Optional<List<User>> users = userRepository.findAllByProfileFirstNameContaining(firstName);
        if(!users.isPresent() || users.get().isEmpty()){
            throw new EmptyListException("No user with first name: " + firstName + " found!");
        }
        else {
            return users.get().stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public List<UserDTO> getAllUsersByLastName(String lastName) throws EmptyListException {
        Optional<List<User>> users = userRepository.findAllByProfileLastNameContaining(lastName);
        if(!users.isPresent() || users.get().isEmpty()){
            throw new EmptyListException("No user with last name: " + lastName + " found!");
        }
        else {
            return users.get().stream().map(this::toDTO).collect(Collectors.toList());
        }
    }

    public UserDTO createUser(UserCreateRequestDTO requestDTO) throws UsernameExistsException {
        if(userRepository.existsByUsername(requestDTO.getUsername())){
            throw new UsernameExistsException("Username: " + requestDTO.getUsername() + " already exists!");
        }
        else {
            User user = new User();
            user.setUsername(requestDTO.getUsername());
            user.setPassword(requestDTO.getPassword());
            Profile userProfile = new Profile();
            userProfile.setFirstName(requestDTO.getFirstName());
            userProfile.setLastName(requestDTO.getLastName());
            userProfile.setDescription(requestDTO.getDescription());
            userProfile.setProfilePictureUrl(requestDTO.getProfilePictureUrl());
            user.setProfile(userProfile);

            return toDTO(userRepository.save(user));
        }
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with id '" + id + "' not found!");
        }
        else {
            userRepository.deleteById(id);
        }
    }

    public UserDTO updateUserById(Long id, UserCreateRequestDTO requestDTO) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with id '" + id + "' not found!");
        }
        return updateUser(user.get(), requestDTO);
    }

    public UserDTO updateUserByUsername(String username, UserCreateRequestDTO requestDTO) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with username '" + username + "' not found!");
        }
        return updateUser(user.get(), requestDTO);
    }

    private UserDTO updateUser(User user, UserCreateRequestDTO requestDTO)
    throws UpdateFailedException {
        User updatedUser = user;
        if(requestDTO.getUsername() != null){
            throw new UpdateFailedException("Username cannot be updated!");
        }
        if(requestDTO.getPassword() != null){
            updatedUser.setPassword(requestDTO.getPassword());
        }
        Profile userProfile = updatedUser.getProfile();
        if(requestDTO.getFirstName() != null){
            userProfile.setFirstName(requestDTO.getFirstName());
        }
        if(requestDTO.getLastName() != null){
            userProfile.setLastName(requestDTO.getLastName());
        }
        if(requestDTO.getDescription() != null){
            userProfile.setDescription(requestDTO.getDescription());
        }
        if(requestDTO.getProfilePictureUrl() != null) {
            userProfile.setProfilePictureUrl(requestDTO.getProfilePictureUrl());
        }
        updatedUser.setProfile(userProfile);
        return toDTO(userRepository.save(updatedUser));
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
