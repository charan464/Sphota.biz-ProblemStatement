package com.example.firstProject.services;

import com.example.firstProject.exception.CategoryAlreadyExistsException;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.repositories.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service class for managing ProductCategory entities
@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("category not found!"));
    }

    // adds a new category to the product category table
    public void addCategory(ProductCategory productCategory) throws CategoryAlreadyExistsException {
        Optional<ProductCategory> existingCategory = productCategoryRepository.findByName(productCategory.getName());
        if (existingCategory.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists!");
        }
        productCategoryRepository.save(productCategory);
    }

}
