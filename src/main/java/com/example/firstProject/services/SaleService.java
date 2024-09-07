package com.example.firstProject.services;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.models.SaleItem;
import com.example.firstProject.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductService productService;

    public Sale createSale(Sale sale){
        double totalTaxAmount = 0.0;
        double totalAmount = 0.0;

        for (SaleItem item : sale.getItems()) {
            Product product = productService.getProductById(item.getProduct().getId());
            ProductCategory productCategory = productCategoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(productCategory);
            double taxAmount = product.getPrice() * productCategory.getGstRate() / 100;
            item.setTaxAmount(taxAmount*item.getQuantity());
            item.setTotalAmount((product.getPrice() + taxAmount) * item.getQuantity());
            item.setProduct(product);
            totalTaxAmount += taxAmount * item.getQuantity();
            totalAmount += item.getTotalAmount();
            item.setSale(sale);
        }

        sale.setTaxAmount(totalTaxAmount);
        sale.setTotalAmount(totalAmount);
        sale.setSaleDate(LocalDate.now());
        return saleRepository.save(sale);
    }

    public List<Sale> getSalesByDate(LocalDate date){
       return saleRepository.findBySaleDate(date);
    }

    public Double getTotalRevenueForCurrentDay(){
        LocalDate startDate=LocalDate.now();
        LocalDate endDate=LocalDate.now();
        return saleRepository.findAllBySaleDateBetween(startDate, endDate)
                .stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

    public Double getTotalRevenueForCurrentMonth(){
        LocalDate startDate=LocalDate.now().withDayOfMonth(1);
        LocalDate endDate=LocalDate.now();
        return saleRepository.findAllBySaleDateBetween(startDate,endDate)
                .stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

    public Double getTotalRevenueForCurrentYear(){
        LocalDate startDate=LocalDate.now().withDayOfYear(1);
        LocalDate endDate=LocalDate.now();
        return saleRepository.findAllBySaleDateBetween(startDate,endDate)
                .stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

}
