package com.example.firstProject.services;

import com.example.firstProject.exception.ProductAlreadyExistsException;
import com.example.firstProject.models.Product;
import com.example.firstProject.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service class for managing Product entities
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product not found!"));
    }

    // adds a new product to the product table
    public void addProduct(Product product) throws ProductAlreadyExistsException {
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists!");
        }
        productRepository.save(product);
    }
}
