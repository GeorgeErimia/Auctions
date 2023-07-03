package com.example.auctions.Repository;

import com.example.auctions.Model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByUserUsername(String username);

    List<Auction> findAllByItemCategoryName(String categoryName);
}
