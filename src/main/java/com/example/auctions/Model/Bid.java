package com.example.auctions.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "placed_by_id")
    private User placedBy;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timeOfPlacing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @PrePersist
    public void initTimeOfPlacing() {
        if (timeOfPlacing == null) {
            timeOfPlacing = LocalDateTime.now();
        }
    }
}
