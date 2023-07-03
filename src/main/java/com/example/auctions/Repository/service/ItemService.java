package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Exceptions.ItemNotFoundException;
import com.example.auctions.Model.Category;
import com.example.auctions.Model.Item;
import com.example.auctions.Model.dtos.CategoryDTO;
import com.example.auctions.Model.dtos.ItemDTO;
import com.example.auctions.Model.dtos.request.ItemCreateRequestDTO;
import com.example.auctions.Repository.CategoryRepository;
import com.example.auctions.Repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<ItemDTO> getAllItems() throws EmptyListException {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new EmptyListException("There are no items!");
        }
        else {
            return items.stream().map(this::toDTO).toList();
        }
    }

    public ItemDTO getItemById(Long id) throws ItemNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()) {
            throw new ItemNotFoundException("Item with id '" + id + "' not found!");
        }
        else {
            return toDTO(item.get());
        }
    }

    public List<ItemDTO> getItemsByNameContaining(String name) throws EmptyListException {
        List<Item> items = itemRepository.findAllByNameContaining(name);
        if (items.isEmpty()) {
            throw new EmptyListException("There are no items containing '" + name + "'!");
        }
        else {
            return items.stream().map(this::toDTO).toList();
        }
    }

    public List<ItemDTO> getItemsByCategoryName(String categoryName) throws EmptyListException {
        List<Item> items = itemRepository.findAllByCategoryName(categoryName.toUpperCase());
        if (items.isEmpty()) {
            throw new EmptyListException("There are no items in category '" + categoryName.toUpperCase() + "'!");
        }
        else {
            return items.stream().map(this::toDTO).toList();
        }
    }

    public ItemDTO createItem(ItemCreateRequestDTO requestDTO)
            throws NullPointerException, CategoryNotFoundException {
        Item item = new Item();
        item.setName(requestDTO.getName());
        item.setDescription(requestDTO.getDescription());
        item.setStartingPrice(requestDTO.getStartingPrice());
        CategoryDTO categoryDTO = categoryService.getCategoryByName(requestDTO.getCategoryName());
        if(categoryDTO == null) {
            throw new CategoryNotFoundException("Category '" + requestDTO.getCategoryName().toUpperCase() + "' not found!");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        item.setCategory(category);
        return toDTO(itemRepository.save(item));
    }

    public void deleteItemById(Long id) throws ItemNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()) {
            throw new ItemNotFoundException("Item with id '" + id + "' not found!");
        }
        else {
            itemRepository.delete(item.get());
        }
    }

    public ItemDTO updateItemById(Long id, ItemCreateRequestDTO requestDTO)
            throws ItemNotFoundException, CategoryNotFoundException {

        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()) {
            throw new ItemNotFoundException("Item with id '" + id + "' not found!");
        }
        else {
            Item itemToUpdate = item.get();
            if(requestDTO.getName() != null) {
                itemToUpdate.setName(requestDTO.getName());
            }
            if(requestDTO.getDescription() != null) {
                itemToUpdate.setDescription(requestDTO.getDescription());
            }
            if(requestDTO.getStartingPrice() != null) {
                itemToUpdate.setStartingPrice(requestDTO.getStartingPrice());
            }
            if(requestDTO.getCategoryName() != null) {
                CategoryDTO categoryDTO = categoryService.getCategoryByName(requestDTO.getCategoryName());
                if(categoryDTO == null) {
                    throw new CategoryNotFoundException("Category '" + requestDTO.getCategoryName() + "' not found!");
                }
                Category category = modelMapper.map(categoryDTO, Category.class);
                itemToUpdate.setCategory(category);
            }
            return toDTO(itemRepository.save(itemToUpdate));
        }
    }

    private ItemDTO toDTO(Item item) throws NullPointerException{
        if(item == null) {
            throw new NullPointerException("Item does not exist!");
        }
        else{
            ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
            String categoryName = item.getCategory().getName();
            if(categoryName == null){
                throw new NullPointerException("Item does not have a category");
            }
            else {
                itemDTO.setCategoryName(categoryName);
            }
            return itemDTO;
        }
    }

}
