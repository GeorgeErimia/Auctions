package com.example.auctions.Repository;

import com.example.auctions.Model.UImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UImageRepository extends JpaRepository<UImage, Long> {
    List<UImage> findAllByAuctionId(Long auctionId);
}
