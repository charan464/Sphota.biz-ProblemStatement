package com.example.firstProject.controllers;

import com.example.firstProject.dto.RevenueSummary;
import com.example.firstProject.exception.CategoryAlreadyExistsException;
import com.example.firstProject.exception.ProductAlreadyExistsException;
import com.example.firstProject.exception.UserAlreadyExistsException;
import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.models.User;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import com.example.firstProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User already exists!", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody ProductCategory productCategory){
        try {
            productCategoryService.addCategory(productCategory);
            return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
        } catch (CategoryAlreadyExistsException e) {
            return new ResponseEntity<>("Category already exists!", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        try {
            productService.addProduct(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>("Product already exists!", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/categories")
    public List<ProductCategory> getAllCategories(){
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/sales/{date}")
    public List<Sale> getSalesByDate(@PathVariable String date){
        return saleService.getSalesByDate(LocalDate.parse(date));
    }

    @GetMapping("/revenue")
    public RevenueSummary getTotalRevenue(){
        RevenueSummary revenueResponse = new RevenueSummary();
        revenueResponse.setRevenueForCurrentDay(saleService.getTotalRevenueForCurrentDay());
        revenueResponse.setRevenueForCurrentMonth(saleService.getTotalRevenueForCurrentMonth());
        revenueResponse.setRevenueForCurrentYear(saleService.getTotalRevenueForCurrentYear());
        return revenueResponse;
    }

}

