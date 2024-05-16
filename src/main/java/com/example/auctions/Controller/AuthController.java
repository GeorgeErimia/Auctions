package com.example.auctions.Controller;

import com.example.auctions.DTO.JwtAuthResponse;
import com.example.auctions.DTO.LoginDTO;
import com.example.auctions.DTO.RegisterDTO;
import com.example.auctions.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private AuthService authService;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
        JwtAuthResponse jwtAuthResponse = authService.login(loginDTO);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
