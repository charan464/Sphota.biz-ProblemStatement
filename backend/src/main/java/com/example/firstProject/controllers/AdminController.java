package com.example.firstProject.controllers;

import com.example.firstProject.dto.ApiResponse;
import com.example.firstProject.dto.RevenueSummary;
import com.example.firstProject.exception.CategoryAlreadyExistsException;
import com.example.firstProject.exception.ProductAlreadyExistsException;
import com.example.firstProject.exception.UserAlreadyExistsException;
import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
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

// handles the requests involved in admin operations

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
    public ResponseEntity<ApiResponse> addUser(@RequestBody User user) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            userService.addUser(user);
            apiResponse.setMessage("User added successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            apiResponse.setMessage("User already exists!");
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            apiResponse.setMessage("Unable to add user!");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody ProductCategory productCategory) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            productCategoryService.addCategory(productCategory);
            apiResponse.setMessage("Category added successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (CategoryAlreadyExistsException e) {
            apiResponse.setMessage("Category already exists!");
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            apiResponse.setMessage("Unable to add category!");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            productService.addProduct(product);
            apiResponse.setMessage("Product added successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            apiResponse.setMessage("Product already exists!");
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            apiResponse.setMessage("Unable to add product!");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() throws Exception {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productCategoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching categories");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching products");
        }
    }

    //returns all the sales recorded in a given date
    @GetMapping("/sales/{date}")
    public ResponseEntity<?> getSalesByDate(@PathVariable String date) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(saleService.getSalesByDate(LocalDate.parse(date)));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching sales");
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getTotalRevenue() {
        try {
            RevenueSummary revenueResponse = new RevenueSummary();
            revenueResponse.setRevenueForCurrentDay(saleService.getTotalRevenueForCurrentDay());
            revenueResponse.setRevenueForCurrentMonth(saleService.getTotalRevenueForCurrentMonth());
            revenueResponse.setRevenueForCurrentYear(saleService.getTotalRevenueForCurrentYear());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(revenueResponse);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching revenue");
        }
    }

}

