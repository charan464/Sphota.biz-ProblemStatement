package com.example.firstProject.service;

import com.example.firstProject.exception.ProductAlreadyExistsException;
import com.example.firstProject.models.Product;
import com.example.firstProject.repositories.ProductRepository;
import com.example.firstProject.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(1L);
        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    void testAddProduct_Success() throws ProductAlreadyExistsException {
        Product product = new Product();
        product.setName("Laptop");
        when(productRepository.findByName("Laptop")).thenReturn(Optional.empty());
        productService.addProduct(product);
    }

    @Test
    void testAddProduct_AlreadyExists() {
        Product product = new Product();
        product.setName("Laptop");
        when(productRepository.findByName("Laptop")).thenReturn(Optional.of(product));
        assertThrows(ProductAlreadyExistsException.class, () -> {
            productService.addProduct(product);
        });
    }
}

