package com.example.auctions.Model.dtos;

import com.example.auctions.Model.dtos.BidDTO;
import com.example.auctions.Model.dtos.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDTO {
    private Long id;
    private LocalDateTime endTime;
    private String itemName;
    private String itemDescription;
    private Double startingPrice;
    private String categoryName;
    private Long userId;
    private Double highestBid;
    private String placedByUsername;
    private LocalDateTime placedAt;
}
