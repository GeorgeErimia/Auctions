package com.example.auctions.Model.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateRequestDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String description;
    private String profilePictureUrl;
}
