package com.example.auctions.Controller;

import com.example.auctions.Exceptions.CategoryAlreadyExistsException;
import com.example.auctions.Exceptions.CategoryNotFoundException;
import com.example.auctions.Exceptions.EmptyListException;
import com.example.auctions.Model.dtos.CategoryDTO;
import com.example.auctions.Repository.service.CategoryService;
import com.example.auctions.Repository.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws EmptyListException {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable final Long id) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable final String name) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryByName(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestParam final String name) throws CategoryAlreadyExistsException {
        return new ResponseEntity<>(categoryService.createCategory(name), HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable final Long id,
                                                           @RequestParam final String name)
            throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.updateCategoryById(id, name), HttpStatus.OK);
    }

    @PutMapping("/name/{categoryName}/update")
    public ResponseEntity<CategoryDTO> updateCategoryByName(@PathVariable final String categoryName,
                                                           @RequestParam final String name)
            throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.updateCategoryByName(categoryName, name), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}/delete")
    public ResponseEntity<String> deleteCategoryById(@PathVariable final Long id)
            throws CategoryNotFoundException {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category with id " + id + " was deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/name/{name}/delete")
    public ResponseEntity<String> deleteCategoryByName(@PathVariable final String name)
            throws CategoryNotFoundException {
        categoryService.deleteCategoryByName(name);
        return new ResponseEntity<>("Category with name " + name + " was deleted successfully", HttpStatus.OK);
    }
}
