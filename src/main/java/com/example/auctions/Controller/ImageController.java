package com.example.auctions.Controller;

import com.example.auctions.DTO.UImageDTO;
import com.example.auctions.Exception.ResourceNotFoundException;
import com.example.auctions.Model.UImage;
import com.example.auctions.Repository.UImageRepository;
import com.example.auctions.Service.ImageService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v2/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private static  final String IMAGES_DIRECTORY = "src/main/resources/static/images/auctions";

    private final ImageService imageService;
    private final UImageRepository imageRepository;
    private final ModelMapper modelMapper;

    // REST API GET endpoint that returns the data of an image entity (id, owner, url)
    @GetMapping("/{id}")
    public ResponseEntity<UImageDTO> getImageData(@PathVariable Long id) {
        // Get the DTO from the repository using imageService
        UImageDTO imageDTO = imageService.getImageDataById(id);

        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    // REST API GET endpoint that returns the actual image when called
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        UImage uImage = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id: " + id+ " not found!"));

        String imageUrl = uImage.getImageUrl();
        try {
            byte[] imageBytes = imageService.getImage(IMAGES_DIRECTORY, imageUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // REST API POST endpoint that adds an image to the databased and links it to the corresponding auction
    @PostMapping("/create/{auctionId}")
    public ResponseEntity<UImageDTO> createImage(@RequestParam("image")MultipartFile image, @RequestParam("default")Boolean isDefault, @PathVariable final Long auctionId) throws IOException {
        String urlImageString = "";
        urlImageString = imageService.saveImageToStorage(IMAGES_DIRECTORY, image);

        UImageDTO savedImage = imageService.createImage(auctionId, urlImageString, isDefault);

        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }

    // REST API GET endpoint that returns all the image DTOS from the database from an auction id
    @GetMapping("/auction/{auctionId}/data")
    public ResponseEntity<List<UImageDTO>> getAllImageData(@PathVariable final Long auctionId) {
        List<UImage> uImages = imageRepository.findAllByAuctionId(auctionId);
        List<UImageDTO> uImageDTOS = uImages.stream().map(uImage -> modelMapper.map(uImage, UImageDTO.class)).toList();
        return new ResponseEntity<>(uImageDTOS, HttpStatus.OK);
    }


}
