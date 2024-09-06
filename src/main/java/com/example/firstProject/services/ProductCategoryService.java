package com.example.firstProject.services;

import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories(){
        return productCategoryRepository.findAll();
    }

    public ProductCategory createCategory(ProductCategory productCategory){
        return productCategoryRepository.save(productCategory);
    }

}
