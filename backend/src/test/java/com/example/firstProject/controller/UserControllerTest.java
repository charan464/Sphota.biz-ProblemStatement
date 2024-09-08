package com.example.firstProject.controller;

import com.example.firstProject.controllers.UserController;
import com.example.firstProject.models.Sale;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private ProductService productService;

    @Mock
    private SaleService saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts_Success() {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = userController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    void testGetAllProducts_Failure() {
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<?> response = userController.getAllProducts();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An error occurred while fetching products", response.getBody());
    }

    @Test
    void testCreateSale_Success() {
        Sale sale = new Sale();
        when(saleService.recordSale(any(Sale.class))).thenReturn(sale);
        ResponseEntity<?> response = userController.recordSale(sale);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sale, response.getBody());
    }

    @Test
    void testCreateSale_Failure() {
        Sale sale = new Sale();
        when(saleService.recordSale(any(Sale.class))).thenThrow(new RuntimeException("Service error"));
        ResponseEntity<?> response = userController.recordSale(sale);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An error occurred while recording the sale", response.getBody());
    }
}

