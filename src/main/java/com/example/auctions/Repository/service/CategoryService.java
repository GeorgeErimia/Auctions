package com.example.auctions.Repository.service;

import com.example.auctions.Exceptions.CategoryAlreadyExistsException;
import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Model.Category;
import com.example.auctions.Model.dtos.CategoryDTO;
import com.example.auctions.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CategoryRepository categoryRepository;


    public List<CategoryDTO> getAllCategories() throws EmptyListException {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EmptyListException("There are no categories!");
        }
        return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }

    public CategoryDTO getCategoryById(final Long categoryId) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category with id '" + categoryId + "' not found!");
        }
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO getCategoryByName(final String name) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findByName(name.toUpperCase());
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category '" + name.toUpperCase() + "' not found!");
        }
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO createCategory(final String categoryName) throws CategoryAlreadyExistsException {
        if(categoryRepository.existsByName(categoryName.toUpperCase())){
            throw new CategoryAlreadyExistsException("Category '" + categoryName.toUpperCase() + "' already exists!");
        }
        else{
            Category category = new Category();
            category.setName(categoryName.toUpperCase());
            categoryRepository.save(category);
            return modelMapper.map(category, CategoryDTO.class);
        }
    }

    public CategoryDTO updateCategoryById(final Long categoryId, final String categoryName)
    throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category with id '" + categoryId + "' not found!");
        }
        category.get().setName(categoryName.toUpperCase());
        categoryRepository.save(category.get());
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO updateCategoryByName(final String categoryName, final String newCategoryName)
        throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findByName(categoryName.toUpperCase());
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category '" + categoryName.toUpperCase() + "' not found!");
        }
        category.get().setName(newCategoryName.toUpperCase());
        categoryRepository.save(category.get());
        return modelMapper.map(category, CategoryDTO.class);
    }

    public void deleteCategoryById(final Long categoryId) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category with id '" + categoryId + "' not found!");
        }
        categoryRepository.delete(category.get());
    }

    public void deleteCategoryByName(final String categoryName) throws CategoryNotFoundException{
        Optional<Category> category = categoryRepository.findByName(categoryName.toUpperCase());
        if(!category.isPresent()){
            throw new CategoryNotFoundException("Category '" + categoryName.toUpperCase() + "' not found!");
        }
        categoryRepository.delete(category.get());
    }
}
