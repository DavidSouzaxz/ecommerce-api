package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Order;
import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/tenant/{slug}")
    public List<Order> listByStore(@PathVariable String slug) {
        return orderRepository.findByTenantSlug(slug);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody String newStatus) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(newStatus.replace("\"", "")); // Remove aspas extras se vierem do JSON
        return orderRepository.save(order);
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        Tenant tenant = order.getTenant();
        if (tenant == null) {
            throw new RuntimeException("Tenant não informado");
        }

        // Verifica se a loja está habilitada e dentro do horário definido (isStoreOpen já trata openTime/closeTime nulos)
        if (!tenant.isOpen() || !tenant.isStoreOpen()) {
            throw new RuntimeException("Loja fechada no momento!");
        }

        order.getItems().forEach(item -> item.setOrder(order));
        order.setStatus("PENDENTE");
        return orderRepository.save(order);
    }

    @GetMapping("/tenant/{slug}/metrics")
    public ResponseEntity<List<Map<String, Object>>> getMetrics(@PathVariable String slug) {
        List<Object[]> data = orderRepository.getSalesDataLast7Days(slug);
        List<Map<String, Object>> result = data.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", row[0].toString());
            map.put("total", row[1]);
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}