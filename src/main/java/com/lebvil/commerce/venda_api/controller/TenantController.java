package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @GetMapping
    public List<Tenant> listAll() {
        return tenantRepository.findAll();
    }

    @PostMapping
    public Tenant create(@RequestBody Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @GetMapping("/{slug}")
    public Tenant findBySlug(@PathVariable String slug) {
        return tenantRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Loja not found"));
    }

}
