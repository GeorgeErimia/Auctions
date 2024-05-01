package com.example.auctions.Repository;

import com.example.auctions.Model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long>{

}
