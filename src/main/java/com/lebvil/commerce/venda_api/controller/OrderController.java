package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Order;
import com.lebvil.commerce.venda_api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        order.getItems().forEach(item -> item.setOrder(order));
        order.setStatus("PENDENTE");
        return orderRepository.save(order);
    }
}