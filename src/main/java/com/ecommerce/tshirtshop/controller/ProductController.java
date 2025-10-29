package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.Product;
import com.ecommerce.tshirtshop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173") // sécuriser ton frontend
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Lire tous les produits
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // ✅ Lire un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit introuvable");
        }
        return ResponseEntity.ok(opt.get());
    }

    // ✅ Ajouter un produit
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        Product saved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Mettre à jour un produit (User Story 12 FIX)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {

        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Produit introuvable");
        }

        Product product = opt.get();

        // ✅ On ne change PAS la catégorie (pour éviter l'erreur dans le front)
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setSku(updatedProduct.getSku());
        product.setBrand(updatedProduct.getBrand());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setDescription(updatedProduct.getDescription()); // ✅ Ajout description

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // ✅ (Optionnel) Mise à jour partielle
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable Long id, @RequestBody Product patch) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit introuvable");
        }

        Product p = opt.get();

        if (patch.getName() != null) p.setName(patch.getName());
        if (patch.getPrice() != 0) p.setPrice(patch.getPrice());
        if (patch.getSku() != null) p.setSku(patch.getSku());
        if (patch.getBrand() != null) p.setBrand(patch.getBrand());
        if (patch.getImageUrl() != null) p.setImageUrl(patch.getImageUrl());
        if (patch.getDescription() != null) p.setDescription(patch.getDescription());

        return ResponseEntity.ok(productRepository.save(p));
    }

    // ✅ Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Produit introuvable");
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Produit supprimé avec succès");
    }
}
