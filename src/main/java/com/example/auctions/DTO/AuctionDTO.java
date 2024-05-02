package com.example.auctions.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDTO {
    private Long id;
    private String name;
    private Long userId;
    private String userUsername;
}
