package com.example.auctions.Model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    Long id;
    LocalDateTime placedAt;
    Double amount;
    Long placedById;
}
