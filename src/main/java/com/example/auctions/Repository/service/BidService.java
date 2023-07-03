package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.*;
import com.example.auctions.Model.Auction;
import com.example.auctions.Model.Bid;
import com.example.auctions.Model.User;
import com.example.auctions.Model.dtos.BidDTO;
import com.example.auctions.Model.dtos.request.BidPlaceRequestDTO;
import com.example.auctions.Repository.AuctionRepository;
import com.example.auctions.Repository.BidRepository;
import com.example.auctions.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    public BidDTO getBidById(Long id) throws BidNotFoundException {
        Optional<Bid> bid = bidRepository.findById(id);
        if(!bid.isPresent()) {
            throw new BidNotFoundException("Bid with id '" + id + "' not found!");
        }
        else {
            return toDTO(bid.get());
        }
    }

    public List<BidDTO> getBidsByAuction(Long auctionId) throws EmptyListException, AuctionNotFoundException{
        List<Bid> bids = bidRepository.findAllByAuctionIdOrderByAmountDesc(auctionId);
        if (bids.isEmpty()) {
            throw new EmptyListException("There are no bids for auction with id '" + auctionId + "'!");
        }
        else {
            return bids.stream().map(this::toDTO).toList();
        }
    }

    public List<BidDTO> getBidsPlacedByUser(String username) throws EmptyListException, UserNotFoundException {
        List<Bid> bids = bidRepository.findAllByPlacedByUsernameOrderByTimeOfPlacingDesc(username);
        if (bids.isEmpty()) {
            throw new EmptyListException("There are no bids placed by user '" + username + "'!");
        }
        else {
            return bids.stream().map(this::toDTO).toList();
        }
    }

    public BidDTO placeBid(BidPlaceRequestDTO requestDTO)
    throws AuctionNotFoundException, UserNotFoundException{
        Optional<Auction> auction = auctionRepository.findById(requestDTO.getAuctionId());
        if(!auction.isPresent()) {
            throw new AuctionNotFoundException("Auction with id '" + requestDTO.getAuctionId() + "' not found!");
        }

        Optional<User> user = userRepository.findByUsername(requestDTO.getUsername());
        if(!user.isPresent()) {
            throw new UserNotFoundException("User '" + requestDTO.getUsername() + "' not found!");
        }

        Bid bid = new Bid();
        bid.setAuction(auction.get());
        bid.setPlacedBy(user.get());
        bid.setAmount(requestDTO.getAmount());

        if(auction.get().checkIfEnded()) {
            throw new AuctionEndedException("We're sorry, but auction with id '" + requestDTO.getAuctionId() + "' has ended!");
        }
        if(auction.get().getHighestBid() != null && auction.get().getHighestBid().getAmount() >= requestDTO.getAmount()) {
            throw new LessAmountException("Your bid must be higher than the current highest bid!");
        }
        if(auction.get().getUser().getId().equals(user.get().getId())) {
            throw new BidBySameUser("You cannot bid on your own auction!");
        }

        auction.get().setHighestBid(bid);

        return toDTO(bidRepository.save(bid));
    }

    public BidDTO toDTO(Bid bid) throws BidNotFoundException {
        if(bid == null) throw new BidNotFoundException();
        BidDTO bidDTO = modelMapper.map(bid, BidDTO.class);
        User user = bid.getPlacedBy();
        bidDTO.setPlacedById(user.getId());
        bidDTO.setPlacedAt(bid.getTimeOfPlacing());
        return bidDTO;
    }
}
