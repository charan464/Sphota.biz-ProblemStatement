package com.example.firstProject.services;

import com.example.firstProject.models.Sale;
import com.example.firstProject.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    public Sale createSale(Sale sale){
        return saleRepository.save(sale);
    }

    public List<Sale> getSalesByDate(LocalDate date){
        return saleRepository.findBySaleDate(date);
    }

    public Double getTotalRevenue(LocalDate date){
        return saleRepository.findBySaleDate(date)
                .stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

}
