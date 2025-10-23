package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.Category;
import com.ecommerce.tshirtshop.model.Product;
import com.ecommerce.tshirtshop.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 🔹 Liste de toutes les catégories
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

    // 🔹 Obtenir les produits d'une catégorie spécifique
    @GetMapping("/{id}/products")
    public List<Product> getProductsByCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(Category::getProducts)
                .orElse(List.of());
    }

    // 🔹 Prendre l’image du produit le plus récent
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
