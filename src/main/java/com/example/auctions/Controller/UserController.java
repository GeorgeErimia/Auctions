package com.example.auctions.Controller;

import com.example.auctions.DTO.UserDTO;
import com.example.auctions.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v2/users")
@CrossOrigin(origins = "http://localhost:3000/*")
public class UserController {

    private final UserService userService;

    // Create a REST API GET endpoint that will return a User from the database
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Create a REST API GET endpoint that will return all Users from the database
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    // Create a REST API POST endpoint that will add a new User to the database
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Create a REST API PUT endpoint that will update a User in the database
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserById(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Create a REST API DELETE endpoint that will delete a User from the database
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable final Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

}
