package com.lebvil.commerce.venda_api.repository;

import com.lebvil.commerce.venda_api.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTenantSlug(String slug);
    List<Product> findByCategory(String category);
}
