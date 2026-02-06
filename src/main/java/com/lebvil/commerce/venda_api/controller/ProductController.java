// java
package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Product;
import com.lebvil.commerce.venda_api.repository.OrderRepository;
import com.lebvil.commerce.venda_api.repository.ProductRepository;
import com.lebvil.commerce.venda_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService service;

    @GetMapping("/tenant/{slug}")
    public List<Product> listByStore(@PathVariable String slug) {
        return repository.findByTenantSlug(slug);
    }

    @GetMapping("/tenant/{slug}/category/{category}")
    public List<Product> listByCategory(@PathVariable String slug, @PathVariable String category) {
        return repository.findByCategory(category);
    }

    @PutMapping("/tenant/{slug}/product/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.upadteProduct(id,product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateAvailability(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return repository.findById(id).map(product -> {
            if (updates.containsKey("available")) {
                product.setAvailable((boolean) updates.get("available"));
            }
            return ResponseEntity.ok(repository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tenant/{slug}/product/{id}")
    public Product getProductById(@PathVariable String slug, @PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return repository.save(product);
    }

    @DeleteMapping("/tenant/{slug}/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = repository.findById(id).orElse(null);
        var orders = orderRepository.findAll();
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        for (var order : orders) {
            if (order.getItems().stream().anyMatch(item -> item.getProduct().getId().equals(id))) {
                return ResponseEntity.status(409).build(); // Conflict
            }
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
