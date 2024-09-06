package com.example.firstProject.services;

import com.example.firstProject.models.Product;
import com.example.firstProject.models.ProductCategory;
import com.example.firstProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product){
        Product createdProduct = productRepository.save(product);
        ProductCategory productCategory = productCategoryService.getCategoryById(product.getCategory().getId());
        createdProduct.setCategory(productCategory);
        return createdProduct;
    }
}
