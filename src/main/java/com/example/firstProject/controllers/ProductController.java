package com.example.firstProject.controllers;

import com.example.firstProject.models.Product;
import com.example.firstProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createCategory(@RequestBody Product product){
        return productService.createProduct(product);
    }
}
