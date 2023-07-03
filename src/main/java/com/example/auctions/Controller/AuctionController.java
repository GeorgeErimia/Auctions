package com.example.auctions.Controller;

import com.example.auctions.Exceptions.AuctionNotFoundException;
import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Model.dtos.AuctionDTO;
import com.example.auctions.Model.dtos.request.AuctionCreateRequestDTO;
import com.example.auctions.Repository.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public ResponseEntity<List<AuctionDTO>> getAllAuctions()
            throws EmptyListException {
        return new ResponseEntity<>(auctionService.getAllAuctions(), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AuctionDTO>> getAllActiveAuctions()
            throws EmptyListException {
        return new ResponseEntity<>(auctionService.getAllActiveAuctions(), HttpStatus.OK);
    }

    @GetMapping("/id/{auctionId}")
    public ResponseEntity<AuctionDTO> getAuctionById(@PathVariable Long auctionId)
            throws AuctionNotFoundException {
        return new ResponseEntity<>(auctionService.findAuctionById(auctionId), HttpStatus.OK);
    }

    @GetMapping("/placedBy/{username}")
    public ResponseEntity<List<AuctionDTO>> getAllAuctionsPlacedByUser(@PathVariable String username)
            throws EmptyListException, UserNotFoundException {
        return new ResponseEntity<>(auctionService.getAllAuctionsPlacedByUser(username), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<AuctionDTO>> getAllAuctionsByCategory(@PathVariable Long categoryName)
            throws CategoryNotFoundException{
        return new ResponseEntity<>(auctionService.getAllAuctionsByCategory(categoryName), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AuctionDTO> createAuction(@RequestBody AuctionCreateRequestDTO requestDTO)
            throws UserNotFoundException, CategoryNotFoundException {
        return new ResponseEntity<>(auctionService.createAuction(requestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{auctionId}/delete")
    public ResponseEntity<String> deleteAuction(@PathVariable Long auctionId)
            throws  AuctionNotFoundException{
        auctionService.deleteAuctionById(auctionId);
        return new ResponseEntity<>("Auction deleted successfully", HttpStatus.OK);
    }
}
