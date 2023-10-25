package com.example.auctions.Controller;

import com.example.auctions.Exceptions.BidNotFoundException;
import com.example.auctions.Model.dtos.BidDTO;
import com.example.auctions.Model.dtos.request.BidPlaceRequestDTO;
import com.example.auctions.Repository.service.BidService;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<BidDTO>> getBidsByAuction(@PathVariable Long auctionId) {
        return new ResponseEntity<>(bidService.getBidsByAuction(auctionId), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<BidDTO>> getBidsPlacedByUser(@PathVariable String username) {
        return new ResponseEntity<>(bidService.getBidsPlacedByUser(username), HttpStatus.OK);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<BidDTO> placeBid(@RequestBody BidPlaceRequestDTO requestDTO) {
        return new ResponseEntity<>(bidService.placeBid(requestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/id/{bidId}")
    public ResponseEntity<String> deleteBid(@PathVariable final long bidId)
        throws BidNotFoundException {
        bidService.deleteBidById(bidId);
        return new ResponseEntity<>("Bid deleted successfully", HttpStatus.OK);
    }
}
