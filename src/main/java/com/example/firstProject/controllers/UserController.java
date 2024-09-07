package com.example.firstProject.controllers;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    SaleService saleService;

    @GetMapping("/categories")
    public List<ProductCategory> getAllCategories(){
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/sales")
    public Sale createSale(@RequestBody Sale sale){
        return saleService.createSale(sale);
    }
}
