package com.example.firstProject.services;

import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories(){
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategoryById(Long id){
        return productCategoryRepository.findById(id).orElse(null);
    }

    public ProductCategory createCategory(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

}
