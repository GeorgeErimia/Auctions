package com.example.auctions.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UImageDTO {
    private Long id;
    private String imageUrl;
    private Boolean isDefault;
    private Long auctionId;
}
