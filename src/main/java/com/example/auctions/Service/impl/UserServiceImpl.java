package com.example.auctions.Service.impl;

import com.example.auctions.DTO.UserDTO;
import com.example.auctions.Exception.ResourceNotFoundException;
import com.example.auctions.Model.User;
import com.example.auctions.Repository.UserRepository;
import com.example.auctions.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    private ModelMapper modelMapper;

    // Method that creates a new user
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Convert DTO to Entity using ModelMapper
        User user = modelMapper.map(userDTO, User.class);
        // Save Entity to DB
        User savedUser = userRepository.save(user);
        // Convert Entity to DTO using ModelMapper
        UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);
        return savedUserDTO;
    }

    // Method that returns a user by it's corresponding ID
    @Override
    public UserDTO getUserById(Long id) {
        // Get the Entity from the DB
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " not found!"));

        // Convert Entity to DTO using ModelMapper
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    // Method that returns all users from the database
    @Override
    public List<UserDTO> getAllUsers() {
        // Get the list of Entities from the DB
        List<User> users = userRepository.findAll();

        // Convert the list of Entities to a list of DTOs using ModelMapper
        List<UserDTO> userDTOS = users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        return userDTOS;
    }

    // Method that updates a user by it's corresponding ID
    @Override
    public UserDTO updateUserById(Long id, UserDTO userDTO) {
        // Get the Entity from the DB
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " not found!"));

        // Update the Entity with the new values
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        // Save the updated Entity to the DB
        User updatedUser = userRepository.save(user);

        // Convert the updated Entity to a DTO using ModelMapper
        UserDTO updatedUserDTO = modelMapper.map(updatedUser, UserDTO.class);

        return updatedUserDTO;
    }

    @Override
    public void deleteUserById(Long id) {
        // Get the Entity from the DB
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " not found!"));

        // Delete the Entity from the DB
        userRepository.delete(user);
    }
}
