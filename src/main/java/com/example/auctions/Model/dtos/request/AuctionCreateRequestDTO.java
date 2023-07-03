package com.example.auctions.Model.dtos.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuctionCreateRequestDTO {
    private String username;
    private String itemName;
    private String itemDescription;
    private Double startingPrice;
    private String categoryName;
}
