package com.example.auctions.Service;

import com.example.auctions.DTO.JwtAuthResponse;
import com.example.auctions.DTO.LoginDTO;
import com.example.auctions.DTO.RegisterDTO;

public interface AuthService {
    String register(RegisterDTO registerDTO);
    JwtAuthResponse login(LoginDTO loginDTO);
}
