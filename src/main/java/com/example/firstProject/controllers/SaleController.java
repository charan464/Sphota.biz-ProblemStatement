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
    public Sale createSale(@RequestBody Sale sale){
        return saleService.createSale(sale);
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
