package com.example.firstProject.repositories;

import com.example.firstProject.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySaleDate(LocalDate date);

    List<Sale> findAllBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
