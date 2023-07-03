package com.example.auctions.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String description;

    @Column
    private String profilePictureUrl;

    public Profile(String firstName, String lastName, String description, String profilePictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.profilePictureUrl = profilePictureUrl;
    }
}
