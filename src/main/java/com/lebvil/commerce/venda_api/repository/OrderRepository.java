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

    // Consulta nativa compatível com PostgreSQL: retorna (date, total) dos últimos 7 dias
    @Query(value = "SELECT CAST(o.created_at AS date) AS date, SUM(o.total_value) AS total\n" +
            "FROM orders o JOIN tenants t ON o.tenant_id = t.id\n" +
            "WHERE t.slug = :slug AND o.created_at >= CURRENT_DATE - INTERVAL '6 days'\n" +
            "GROUP BY CAST(o.created_at AS date) ORDER BY date ASC", nativeQuery = true)
    List<Object[]> getSalesDataLast7Days(@Param("slug") String slug);
}
