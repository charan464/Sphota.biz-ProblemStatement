package com.example.firstProject.controllers;

import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getAllCategories(){
        return productCategoryService.getAllCategories();
    }

    @PostMapping
    public ProductCategory createCategory(@RequestBody ProductCategory productCategory){
        return productCategoryService.createCategory(productCategory);
    }


}
