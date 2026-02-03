package com.lebvil.commerce.venda_api.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private  String role;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}