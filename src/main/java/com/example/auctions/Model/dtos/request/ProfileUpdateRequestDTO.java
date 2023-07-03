package com.example.auctions.Model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileUpdateRequestDTO {
    private String firstName;
    private String lastName;
    private String description;
    private String profilePictureUrl;
}

