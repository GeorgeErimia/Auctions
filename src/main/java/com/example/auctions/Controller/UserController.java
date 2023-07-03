package com.example.auctions.Controller;

import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Exceptions.UsernameExistsException;
import com.example.auctions.Model.dtos.request.UserCreateRequestDTO;
import com.example.auctions.Model.dtos.UserDTO;
import com.example.auctions.Repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() throws EmptyListException {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable final String username) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<UserDTO>> getAllUsersByFirstName(@PathVariable final String firstName) throws EmptyListException {
        return new ResponseEntity<>(userService.getAllUsersByFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<UserDTO>> getAllUsersByLastName(@PathVariable final String lastName) throws EmptyListException {
        return new ResponseEntity<>(userService.getAllUsersByLastName(lastName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequestDTO requestDTO)
            throws UsernameExistsException {
        return new ResponseEntity<>(userService.createUser(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserCreateRequestDTO requestDTO)
            throws UserNotFoundException {
        return new ResponseEntity<>(userService.updateUserById(id, requestDTO), HttpStatus.OK);
    }

    @PutMapping("/{username}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @RequestBody UserCreateRequestDTO requestDTO)
            throws UserNotFoundException {
        return new ResponseEntity<>(userService.updateUserByUsername(username, requestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User with id " + id + " was deleted!", HttpStatus.OK);
    }
}
