package com.example.firstProject.controllers;

import com.example.firstProject.models.Sale;
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

    @PostMapping
    public List<Sale> createSale(@RequestBody List<Sale> sales){
        for(Sale sale:sales){
            double taxAmount=sale.getProduct().getPrice()*sale.getProduct().getCategory().getGstRate()/100;
            sale.setTaxAmount(taxAmount);
            sale.setTotalAmount(sale.getProduct().getPrice()+taxAmount);
            saleService.createSale(sale);
        }
        return sales;
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
