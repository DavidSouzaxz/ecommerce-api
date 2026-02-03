package com.lebvil.commerce.venda_api.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name= "tenants")
@Data
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    private String primaryColor;

    private String logoUrl;
}
