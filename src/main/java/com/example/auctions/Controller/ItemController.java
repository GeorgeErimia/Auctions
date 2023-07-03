package com.example.auctions.Controller;

import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.ItemNotFoundException;
import com.example.auctions.Model.dtos.ItemDTO;
import com.example.auctions.Model.dtos.request.ItemCreateRequestDTO;
import com.example.auctions.Repository.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() throws EmptyListException {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) throws ItemNotFoundException {
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDTO>> getItemsByNameContaining(@RequestParam String name)
        throws EmptyListException {
        return new ResponseEntity<>(itemService.getItemsByNameContaining(name), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ItemDTO>> getItemsByCategory(@PathVariable String categoryName)
        throws EmptyListException {
        return new ResponseEntity<>(itemService.getItemsByCategoryName(categoryName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemCreateRequestDTO requestDTO)
        throws CategoryNotFoundException {
        return new ResponseEntity<>(itemService.createItem(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemCreateRequestDTO requestDTO)
        throws ItemNotFoundException, CategoryNotFoundException {
        return new ResponseEntity<>(itemService.updateItemById(id, requestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}/delete")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) throws ItemNotFoundException {
        itemService.deleteItemById(id);
        return new ResponseEntity<>("Item with id " + id + " was deleted", HttpStatus.OK);
    }
}
