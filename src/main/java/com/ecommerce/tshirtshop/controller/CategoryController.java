package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.Category;
import com.ecommerce.tshirtshop.model.Product;
import com.ecommerce.tshirtshop.repository.CategoryRepository;
import com.ecommerce.tshirtshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
// (n’oublie pas d’ajouter cet import en haut du fichier)

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(cat -> new CategoryResponse(
                        cat.getId(),
                        cat.getName(),
                        cat.getDescription(),
                        getLatestProductImage(cat)
                ))
                .collect(Collectors.toList());
    }

    // ✅ Nouvelle méthode correcte : charge les produits depuis ProductRepository
    @GetMapping("/{id}/products")
    public List<Product> getProductsByCategory(@PathVariable Long id) {
        return productRepository.findByCategoryId(id);
    }

    private String getLatestProductImage(Category cat) {
        if (cat.getProducts() == null || cat.getProducts().isEmpty()) {
            return null;
        }

        return cat.getProducts().stream()
                .max(Comparator.comparing(Product::getId))
                .map(Product::getImageUrl)
                .orElse(null);
    }

    public record CategoryResponse(Long id, String name, String description, String imageUrl) {}
}
