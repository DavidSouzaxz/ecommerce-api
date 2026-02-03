// java
package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Product;
import com.lebvil.commerce.venda_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping("/tenant/{slug}")
    public List<Product> listByStore(@PathVariable String slug) {
        return repository.findByTenantSlug(slug);
    }

    @GetMapping("/tenant/{slug}/category/{category}")
    public List<Product> listByCategory(@PathVariable String slug, @PathVariable String category) {
        return repository.findByCategory(category);
    }

    @GetMapping("/tenant/{slug}/product/{id}")
    public Product getProductById(@PathVariable String slug, @PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return repository.save(product);
    }
}
