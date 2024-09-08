package com.example.firstProject.controllers;

import com.example.firstProject.models.Sale;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// handles the requests involved in user operations

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ProductService productService;

    @Autowired
    SaleService saleService;

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

    //records a sale and returns the bill
    @PostMapping("/recordSaleAndGenerateBill")
    public ResponseEntity<?> recordSale(@RequestBody Sale sale) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(saleService.recordSale(sale));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while recording the sale");
        }
    }
}
