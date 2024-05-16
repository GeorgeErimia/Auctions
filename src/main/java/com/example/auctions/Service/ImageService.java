package com.example.auctions.Service;

import com.example.auctions.DTO.UImageDTO;
import com.example.auctions.Model.UImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException;

    public byte[] getImage(String imageDirectory, String imageName) throws IOException;

    public UImageDTO getImageDataById(Long id);

    public UImageDTO createImage(Long auctionId, String imageUrl, Boolean isDefault);

    public String deleteImage(String imageDirectory, String imageName) throws IOException;

}
