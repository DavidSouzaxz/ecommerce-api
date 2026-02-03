package com.lebvil.commerce.venda_api.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private Double price;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}
