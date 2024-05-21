package com.example.auctions.Service.impl;

import com.example.auctions.DTO.UImageDTO;
import com.example.auctions.Exception.ResourceNotFoundException;
import com.example.auctions.Model.UImage;
import com.example.auctions.Repository.UImageRepository;
import com.example.auctions.Service.ImageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final UImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            Path uploadPath = Path.of(uploadDirectory);
            Path filePath = uploadPath.resolve(uniqueFileName);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public byte[] getImage(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if(Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            return null;
        }
    }

    @Override
    public UImageDTO getImageDataById(Long id) {
        // Get the entity from the database
        UImage uImage = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id: " + id+ " not found!"));

        // Convert Entity to DTO using modelmapper
        UImageDTO uImageDTO = modelMapper.map(uImage, UImageDTO.class);

        return  uImageDTO;
    }

    @Override
    public UImageDTO createImage(Long auctionId, String imageUrl, Boolean isDefault) {
        // Create a DTO based on the parameters
        UImageDTO uImageDTO = new UImageDTO();
        uImageDTO.setAuctionId(auctionId);
        uImageDTO.setImageUrl(imageUrl);
//        uImageDTO.setIsDefault(isDefault);

        // Make the image default if the image is the first image for the auction
        try {
            UImage defaultImage = imageRepository.findByAuctionIdAndIsDefault(auctionId, true);
            uImageDTO.setIsDefault(false);
        } catch (Exception e) {
            uImageDTO.setIsDefault(true);
        }

        // Convert the DTO to entity using ModelMapper
        UImage uImage = modelMapper.map(uImageDTO, UImage.class);

        // Save the Image to the database
        UImage savedimage = imageRepository.save(uImage);

        // Convert the Entity back to DTO
        UImageDTO savedImageDTO = modelMapper.map(savedimage, UImageDTO.class);
        return savedImageDTO;
    }

    @Override
    public String deleteImage(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if(Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Image Deleted!";
        } else {
            return "Deletion Failed!";
        }
    }

    @Override
    public UImageDTO makeDefault(Long id) {
        UImage uImage = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id: " + id + " NOT FOUND!"));

        try {
            UImage defaultImage = imageRepository.findByAuctionIdAndIsDefault(uImage.getAuction().getId(), true);
            defaultImage.setIsDefault(false);
            imageRepository.save(defaultImage);
        } catch (Exception e) {
            // No default image found
        } finally {
            uImage.setIsDefault(true);

            UImage updatedImage = imageRepository.save(uImage);

            UImageDTO uImageDTO = modelMapper.map(updatedImage, UImageDTO.class);

            return uImageDTO;
        }
    }

    @Override
    public UImageDTO getDefaultImage(Long auctionId) {
        try {
            UImage uImage = imageRepository.findByAuctionIdAndIsDefault(auctionId, true);
            UImageDTO uImageDTO = modelMapper.map(uImage, UImageDTO.class);
            return uImageDTO;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Default Image for auction with id: " + auctionId + " NOT FOUND!");
        }
    }
}
