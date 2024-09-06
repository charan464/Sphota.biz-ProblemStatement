package com.example.firstProject.controllers;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.models.SaleItem;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    SaleService saleService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductService productService;

    @PostMapping
    public Sale createSale(@RequestBody Sale sale){
        double totalTaxAmount = 0.0;
        double totalAmount = 0.0;

        for (SaleItem item : sale.getItems()) {
            Product product = productService.getProductById(item.getProduct().getId());
            ProductCategory productCategory = productCategoryService.getCategoryById(product.getCategory().getId());
            double taxAmount = product.getPrice() * productCategory.getGstRate() / 100;
            item.setTaxAmount(taxAmount*item.getQuantity());
            item.setTotalAmount((product.getPrice() + taxAmount) * item.getQuantity());
            totalTaxAmount += taxAmount * item.getQuantity();
            totalAmount += item.getTotalAmount();
        }

        sale.setTaxAmount(totalTaxAmount);
        sale.setTotalAmount(totalAmount);
        sale.setSaleDate(LocalDate.now());
        return saleService.createSale(sale);
    }



    @GetMapping("/date/{date}")
    public List<Sale> getSalesByDate(@PathVariable String date){
        return saleService.getSalesByDate(LocalDate.parse(date));
    }

    @GetMapping("/revenue/{date}")
    public double getTotalRevenue(@PathVariable String date){
        return saleService.getTotalRevenue(LocalDate.parse(date));
    }

}
