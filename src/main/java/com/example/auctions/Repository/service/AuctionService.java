package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.AuctionNotFoundException;
import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.UserNotFoundException;
import com.example.auctions.Model.*;
import com.example.auctions.Model.dtos.AuctionDTO;
import com.example.auctions.Model.dtos.request.AuctionCreateRequestDTO;
import com.example.auctions.Repository.AuctionRepository;
import com.example.auctions.Repository.CategoryRepository;
import com.example.auctions.Repository.ItemRepository;
import com.example.auctions.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private static final ModelMapper modelMapper = new ModelMapper();


    // Get All Auctions
    public List<AuctionDTO> getAllAuctions()
            throws EmptyListException {
        List<Auction> auctions = auctionRepository.findAll();
        if(auctions.isEmpty()){
            throw new EmptyListException("There are no auctions!");
        }
        return auctions.stream().map(this::toDTO).toList();
    }

    public List<AuctionDTO> getAllActiveAuctions()
            throws EmptyListException {
        List<Auction> auctions = auctionRepository.findAll();
        if(auctions.isEmpty()){
            throw new EmptyListException("There are no auctions!");
        }
        List<Auction> activeAuctions = auctions.stream().filter(auction -> auction.checkIfEnded()).toList();
        if(activeAuctions.isEmpty()){
            throw new EmptyListException("There are no active auctions right now!");
        }
        return auctions.stream().map(this::toDTO).toList();
    }

    public AuctionDTO findAuctionById(Long auctionId)
            throws AuctionNotFoundException {
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        if(!auction.isPresent()){
            throw new AuctionNotFoundException("Auction with id '" + auctionId + "' not found!");
        }
        return toDTO(auction.get());
    }

    public List<AuctionDTO> getAllAuctionsPlacedByUser(String username)
            throws EmptyListException, UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UserNotFoundException("User '" + username + "' not found!");
        }
        List<Auction> auctions = auctionRepository.findAllByUserUsername(username);
        if(auctions.isEmpty()){
            throw new AuctionNotFoundException("User '" + username + "' has no auctions!");
        }
        return auctions.stream().map(this::toDTO).toList();
    }

    public List<AuctionDTO> getAllAuctionsByCategory(Long categoryName)
            throws EmptyListException, CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryName);
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category '" + categoryName + "' not found!");
        }
        List<Auction> auctions = auctionRepository.findAllByItemCategoryName(category.get().getName());
        if(auctions.isEmpty()){
            throw new EmptyListException("There are no auctions in category '" + categoryName + "'!");
        }
        return auctions.stream().map(this::toDTO).toList();
    }

    public AuctionDTO createAuction(AuctionCreateRequestDTO requestDTO)
            throws UserNotFoundException, CategoryNotFoundException {
        Auction auction = new Auction();
        Optional<User> user = userRepository.findByUsername(requestDTO.getUsername());
        if(!user.isPresent()){
            throw new UserNotFoundException("User '" + requestDTO.getUsername() + "' not found!");
        }
        Optional<Category> category = categoryRepository.findByName(requestDTO.getCategoryName());
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category '" + requestDTO.getCategoryName() + "' not found!");
        }
        Item item = new Item(
                requestDTO.getItemName(),
                requestDTO.getItemDescription(),
                requestDTO.getStartingPrice()
        );
        item.setCategory(category.get());

        auction.setItem(item);

        auction.setUser(user.get());
        return toDTO(auctionRepository.save(auction));
    }

    public void deleteAuctionById(Long auctionId)
            throws AuctionNotFoundException {
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        if(!auction.isPresent()){
            throw new AuctionNotFoundException("Auction with id '" + auctionId + "' not found!");
        }
        auctionRepository.deleteById(auctionId);
    }

    public AuctionDTO toDTO(Auction auction)
            throws AuctionNotFoundException{
        if(auction == null){
            throw new AuctionNotFoundException("Auction not found!");
        }
        AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
        auctionDTO.setItemName(auction.getItem().getName());
        auctionDTO.setItemDescription(auction.getItem().getDescription());
        auctionDTO.setStartingPrice(auction.getItem().getStartingPrice());
        auctionDTO.setCategoryName(auction.getItem().getCategory().getName());
        auctionDTO.setUserId(auction.getUser().getId());
        auctionDTO.setPlacedByUsername(auction.getUser().getUsername());
        if(auction.getHighestBid() != null){
            auctionDTO.setHighestBid(auction.getHighestBid().getAmount());
        }
        return auctionDTO;
    }
}
