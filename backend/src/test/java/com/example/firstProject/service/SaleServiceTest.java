package com.example.firstProject.service;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.Sale;
import com.example.firstProject.models.SaleItem;
import com.example.firstProject.repositories.SaleRepository;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ProductCategoryService productCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSale_Success() {
        ProductCategory category = new ProductCategory();
        category.setId(1L);
        category.setGstRate(10.0);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setCategory(category);
        SaleItem saleItem = new SaleItem();
        saleItem.setProduct(product);
        saleItem.setQuantity(2);
        Sale sale = new Sale();
        sale.setItems(Arrays.asList(saleItem));
        when(productService.getProductById(1L)).thenReturn(product);
        when(productCategoryService.getCategoryById(1L)).thenReturn(category);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Sale savedSale = saleService.recordSale(sale);
        assertNotNull(savedSale);
        assertEquals(20.0, savedSale.getTaxAmount());
        assertEquals(220.0, savedSale.getTotalAmount());
        assertEquals(LocalDate.now(), savedSale.getSaleDate());
    }

    @Test
    void testGetSalesByDate() {
        LocalDate date = LocalDate.now();
        Sale sale = new Sale();
        sale.setSaleDate(date);
        when(saleRepository.findBySaleDate(date)).thenReturn(Arrays.asList(sale));
        List<Sale> sales = saleService.getSalesByDate(date);
        assertNotNull(sales);
        assertEquals(1, sales.size());
        assertEquals(date, sales.get(0).getSaleDate());
    }

    @Test
    void testGetTotalRevenueForCurrentDay() {
        Sale sale1 = new Sale();
        sale1.setTotalAmount(100.0);
        Sale sale2 = new Sale();
        sale2.setTotalAmount(200.0);
        LocalDate today = LocalDate.now();
        when(saleRepository.findAllBySaleDateBetween(today, today)).thenReturn(Arrays.asList(sale1, sale2));
        Double totalRevenue = saleService.getTotalRevenueForCurrentDay();
        assertNotNull(totalRevenue);
        assertEquals(300.0, totalRevenue);
    }

    @Test
    void testGetTotalRevenueForCurrentMonth() {
        Sale sale1 = new Sale();
        sale1.setTotalAmount(150.0);
        Sale sale2 = new Sale();
        sale2.setTotalAmount(250.0);
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        when(saleRepository.findAllBySaleDateBetween(startOfMonth, today)).thenReturn(Arrays.asList(sale1, sale2));
        Double totalRevenue = saleService.getTotalRevenueForCurrentMonth();
        assertNotNull(totalRevenue);
        assertEquals(400.0, totalRevenue);
    }

    @Test
    void testGetTotalRevenueForCurrentYear() {
        Sale sale1 = new Sale();
        sale1.setTotalAmount(500.0);
        Sale sale2 = new Sale();
        sale2.setTotalAmount(600.0);
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate today = LocalDate.now();
        when(saleRepository.findAllBySaleDateBetween(startOfYear, today)).thenReturn(Arrays.asList(sale1, sale2));
        Double totalRevenue = saleService.getTotalRevenueForCurrentYear();
        assertNotNull(totalRevenue);
        assertEquals(1100.0, totalRevenue);
    }
}
