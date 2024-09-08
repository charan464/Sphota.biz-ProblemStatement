package com.example.firstProject.controller;

import com.example.firstProject.controllers.AdminController;
import com.example.firstProject.dto.ApiResponse;
import com.example.firstProject.dto.RevenueSummary;
import com.example.firstProject.exception.CategoryAlreadyExistsException;
import com.example.firstProject.exception.ProductAlreadyExistsException;
import com.example.firstProject.exception.UserAlreadyExistsException;
import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.models.User;
import com.example.firstProject.services.ProductCategoryService;
import com.example.firstProject.services.ProductService;
import com.example.firstProject.services.SaleService;
import com.example.firstProject.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @Mock
    private SaleService saleService;

    @Mock
    private ProductCategoryService productCategoryService;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser_Success() throws UserAlreadyExistsException {
        User user = new User();
        doNothing().when(userService).addUser(user);
        ResponseEntity<ApiResponse> response = adminController.addUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User added successfully", response.getBody().getMessage());
    }

    @Test
    void testAddUser_UserAlreadyExists() throws UserAlreadyExistsException {
        User user = new User();
        doThrow(UserAlreadyExistsException.class).when(userService).addUser(user);
        ResponseEntity<ApiResponse> response = adminController.addUser(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists!", response.getBody().getMessage());
    }

    @Test
    void testAddUser_UnableToAddUser() throws UserAlreadyExistsException {
        User user = new User();
        doThrow(RuntimeException.class).when(userService).addUser(user);
        ResponseEntity<ApiResponse> response = adminController.addUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to add user!", response.getBody().getMessage());
    }

    @Test
    void testAddCategory_Success() throws CategoryAlreadyExistsException {
        ProductCategory category = new ProductCategory();
        doNothing().when(productCategoryService).addCategory(category);
        ResponseEntity<ApiResponse> response = adminController.addCategory(category);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody().getMessage());
    }

    @Test
    void testAddCategory_CategoryAlreadyExists() throws CategoryAlreadyExistsException {
        ProductCategory category = new ProductCategory();
        doThrow(CategoryAlreadyExistsException.class).when(productCategoryService).addCategory(category);
        ResponseEntity<ApiResponse> response = adminController.addCategory(category);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Category already exists!", response.getBody().getMessage());
    }

    @Test
    void testAddCategory_UnableToAddCategory() throws CategoryAlreadyExistsException {
        ProductCategory category = new ProductCategory();
        doThrow(RuntimeException.class).when(productCategoryService).addCategory(category);
        ResponseEntity<ApiResponse> response = adminController.addCategory(category);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to add category!", response.getBody().getMessage());
    }

    @Test
    void testAddProduct_Success() throws ProductAlreadyExistsException {
        Product product = new Product();
        doNothing().when(productService).addProduct(product);
        ResponseEntity<ApiResponse> response = adminController.addProduct(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product added successfully", response.getBody().getMessage());
    }

    @Test
    void testAddProduct_ProductAlreadyExists() throws ProductAlreadyExistsException {
        Product product = new Product();
        doThrow(ProductAlreadyExistsException.class).when(productService).addProduct(product);
        ResponseEntity<ApiResponse> response = adminController.addProduct(product);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Product already exists!", response.getBody().getMessage());
    }

    @Test
    void testAddProduct_UnableToAddProduct() throws ProductAlreadyExistsException {
        Product product = new Product();
        doThrow(RuntimeException.class).when(productService).addProduct(product);
        ResponseEntity<ApiResponse> response = adminController.addProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to add product!", response.getBody().getMessage());
    }

    @Test
    void testGetAllCategories_Success() throws Exception {
        when(productCategoryService.getAllCategories()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = adminController.getAllCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllCategories_Failure() throws Exception {
        doThrow(RuntimeException.class).when(productCategoryService).getAllCategories();
        ResponseEntity<?> response = adminController.getAllCategories();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetAllProducts_Success() {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = adminController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllProducts_Failure() {
        doThrow(RuntimeException.class).when(productService).getAllProducts();
        ResponseEntity<?> response = adminController.getAllProducts();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetSalesByDate_Success() {
        LocalDate date = LocalDate.now();
        when(saleService.getSalesByDate(date)).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = adminController.getSalesByDate(date.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetSalesByDate_Failure() {
        LocalDate date = LocalDate.now();
        doThrow(RuntimeException.class).when(saleService).getSalesByDate(date);
        ResponseEntity<?> response = adminController.getSalesByDate(date.toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetTotalRevenue_Success() {
        when(saleService.getTotalRevenueForCurrentDay()).thenReturn(100.0);
        when(saleService.getTotalRevenueForCurrentMonth()).thenReturn(1000.0);
        when(saleService.getTotalRevenueForCurrentYear()).thenReturn(10000.0);
        ResponseEntity<?> response = adminController.getTotalRevenue();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100.0, ((RevenueSummary) response.getBody()).getRevenueForCurrentDay());
        assertEquals(1000.0, ((RevenueSummary) response.getBody()).getRevenueForCurrentMonth());
        assertEquals(10000.0, ((RevenueSummary) response.getBody()).getRevenueForCurrentYear());
    }

    @Test
    void testGetTotalRevenue_Failure() {
        doThrow(RuntimeException.class).when(saleService).getTotalRevenueForCurrentDay();
        ResponseEntity<?> response = adminController.getTotalRevenue();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

