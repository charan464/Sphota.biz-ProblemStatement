package com.example.firstProject.service;

import com.example.firstProject.exception.CategoryAlreadyExistsException;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.repositories.ProductCategoryRepository;
import com.example.firstProject.services.ProductCategoryService;
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

class ProductCategoryServiceTest {

    @InjectMocks
    private ProductCategoryService productCategoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        ProductCategory category = new ProductCategory();
        when(productCategoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        List<ProductCategory> categories = productCategoryService.getAllCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
    }

    @Test
    void testGetCategoryById_Found() {
        ProductCategory category = new ProductCategory();
        when(productCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        ProductCategory foundCategory = productCategoryService.getCategoryById(1L);
        assertNotNull(foundCategory);
        assertEquals(category, foundCategory);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(productCategoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            productCategoryService.getCategoryById(1L);
        });
    }

    @Test
    void testAddCategory_Success() throws CategoryAlreadyExistsException {
        ProductCategory category = new ProductCategory();
        category.setName("Electronics");
        when(productCategoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        productCategoryService.addCategory(category);
    }

    @Test
    void testAddCategory_AlreadyExists() {
        ProductCategory category = new ProductCategory();
        category.setName("Electronics");
        when(productCategoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));
        assertThrows(CategoryAlreadyExistsException.class, () -> {
            productCategoryService.addCategory(category);
        });
    }
}

