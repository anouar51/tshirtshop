package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.Product;
import com.ecommerce.tshirtshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173") // autorise React
public class ProductController {

    private final ProductRepository productRepository;

    // ✅ Constructeur
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Récupérer tous les produits
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ Récupérer un produit par son ID
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    // ✅ Récupérer les produits d'une catégorie
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
