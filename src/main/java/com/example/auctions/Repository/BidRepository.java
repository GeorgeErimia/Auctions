package com.example.auctions.Repository;

import com.example.auctions.Model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByAuctionIdOrderByAmountDesc(Long auctionId);
    List<Bid> findAllByPlacedByUsernameOrderByTimeOfPlacingDesc(String username);
}
