package com.example.firstProject.controllers;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.models.User;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import com.example.firstProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    SaleService saleService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductService productService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        userService.saveUser(user);
        return "User added successfully";
    }

    @PostMapping("/addCategory")
    public ProductCategory addCategory(@RequestBody ProductCategory productCategory){
        return productCategoryService.addCategory(productCategory);
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping("/categories")
    public List<ProductCategory> getAllCategories(){
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/date/{date}")
    public List<Sale> getSalesByDate(@PathVariable String date){
        return saleService.getSalesByDate(LocalDate.parse(date));
    }

    @GetMapping("/revenue/{type}")
    public double getTotalRevenue(@PathVariable String type){
        switch(type){
            case "day": return saleService.getTotalRevenueForCurrentDay();
            case "month": return saleService.getTotalRevenueForCurrentMonth();
            case "year":return saleService.getTotalRevenueForCurrentYear();
        }
        return 0;
    }

}

