package com.example.auctions.Service;

import com.example.auctions.DTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUserById(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

}
