package com.lebvil.commerce.venda_api.entitys;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerAddress;
    private Double totalValue;
    private String status; // Ex: "PENDENTE", "PREPARANDO", "ENTREGUE"

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
