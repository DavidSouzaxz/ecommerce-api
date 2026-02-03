package com.lebvil.commerce.venda_api.repository;

import com.lebvil.commerce.venda_api.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTenantSlug(String slug);

    @Query("SELECT SUM(o.totalValue) FROM Order o WHERE o.tenant.slug = :slug")
    Double sumTotalValueByTenantSlug(@Param("slug") String slug);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.tenant.slug = :slug")
    Long countByTenantSlug(@Param("slug") String slug);
}
