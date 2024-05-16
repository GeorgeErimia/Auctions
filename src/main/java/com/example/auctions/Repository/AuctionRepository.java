package com.example.auctions.Repository;

import com.example.auctions.DTO.AuctionDTO;
import com.example.auctions.Model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long>{

    Auction findByUserId(Long userId);

    List<Auction> findByUserUsername(String userUsername);

}
