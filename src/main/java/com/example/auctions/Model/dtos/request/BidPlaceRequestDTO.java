package com.example.auctions.Model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidPlaceRequestDTO {
    private Long auctionId;
    private Double amount;
    private String username;
}
