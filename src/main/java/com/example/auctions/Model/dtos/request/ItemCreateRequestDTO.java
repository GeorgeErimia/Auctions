package com.example.auctions.Model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemCreateRequestDTO {
    private String name;
    private String description;
    private Double startingPrice;
    private String categoryName;
}
