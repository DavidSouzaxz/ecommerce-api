package com.lebvil.commerce.venda_api.repository;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findBySlug(String slug);
}
