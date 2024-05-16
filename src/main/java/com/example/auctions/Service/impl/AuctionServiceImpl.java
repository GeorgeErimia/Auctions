package com.example.auctions.Service.impl;

import com.example.auctions.DTO.AuctionDTO;
import com.example.auctions.Exception.ResourceNotFoundException;
import com.example.auctions.Model.Auction;
import com.example.auctions.Repository.AuctionRepository;
import com.example.auctions.Service.AuctionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    AuctionRepository auctionRepository;

    private ModelMapper modelMapper;

    // Method that will create a new auction
    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        // Convert DTO to Entity using ModelMapper
        Auction auction = modelMapper.map(auctionDTO, Auction.class);
        // Set the end date of the auction to be two days from the current date
        auction.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
        // Set the description of the auction
        auction.setDescription(auctionDTO.getDescription());
        // Save Entity to DB
        Auction savedAuction = auctionRepository.save(auction);
        // Convert Entity to DTO using ModelMapper
        AuctionDTO savedAuctionDTO = modelMapper.map(savedAuction, AuctionDTO.class);
        return savedAuctionDTO;
    }

    // Method that will return an auction by it's corresponding ID
    @Override
    public AuctionDTO getAuctionById(Long id) {
        // Get the Entity from the DB
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Auction with ID: " + id + " not found!"));

        // Convert Entity to DTO using ModelMapper
        AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
        return auctionDTO;
    }

    // Method that will return an auction by it's corresponding User ID
    @Override
    public AuctionDTO getAuctionByUserId(Long userId) {
        // Get the Entity from the DB
        Auction auction = auctionRepository.findByUserId(userId);

        // Convert Entity to DTO using ModelMapper
        AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
        return auctionDTO;
    }

    // Method that will return an auction by it's corresponding User Username
    @Override
    public List<AuctionDTO> getAuctionsByUserUsername(String userUsername) {
        // Get the Entity from the DB
        List<Auction> auctions = auctionRepository.findByUserUsername(userUsername);

        // Convert Entity to DTO using ModelMapper
        List<AuctionDTO> auctionDTOS = auctions.stream().map(auction -> modelMapper.map(auction, AuctionDTO.class)).collect(Collectors.toList());
        return auctionDTOS;
    }

    // Method that will return all auctions from the database
    @Override
    public List<AuctionDTO> getAllAuctions() {
        // Get the list of Entities from the DB
        List<Auction> auctions = auctionRepository.findAll();

        // Convert the list of Entities to a list of DTOs using ModelMapper
        List<AuctionDTO> auctionDTOS = auctions
                .stream()
                .map(auction -> modelMapper.map(auction, AuctionDTO.class))
                .collect(Collectors.toList());

        return auctionDTOS;
    }

    // Method that will update an auction by it's corresponding ID
    @Override
    public AuctionDTO updateAuctionById(Long id, AuctionDTO auctionDTO) {
        // Get the Entity from the DB
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Auction with ID: " + id + " not found!"));

        // Update the Entity with the new values
        auction.setName(auctionDTO.getName());

        auction.setDescription(auctionDTO.getDescription());

        // Save the updated Entity to the DB
        Auction updatedAuction = auctionRepository.save(auction);

        // Convert the updated Entity to a DTO using ModelMapper
        AuctionDTO updatedAuctionDTO = modelMapper.map(updatedAuction, AuctionDTO.class);

        return updatedAuctionDTO;
    }

    @Override
    public void deleteAuctionById(Long id) {
        // Get the Entity from the DB
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Auction with ID: " + id + " not found!"));
        auctionRepository.delete(auction);
    }
}
