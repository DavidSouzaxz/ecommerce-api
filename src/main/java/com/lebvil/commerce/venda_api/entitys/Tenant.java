package com.lebvil.commerce.venda_api.entitys;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;

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

    public boolean isStoreOpen() {
        if (openTime == null || closeTime == null) return true;

        LocalTime now = LocalTime.now();

        if (openTime.isBefore(closeTime)) {
            return !now.isBefore(openTime) && !now.isAfter(closeTime);
        }
        return now.isAfter(openTime) || now.isBefore(closeTime);
    }
    private LocalTime openTime;
    private LocalTime closeTime;

    @Column(name = "is_open", nullable = false)
    @ColumnDefault("true")
    private boolean isOpen = true;
}
