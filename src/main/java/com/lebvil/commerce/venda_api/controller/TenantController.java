package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.repository.TenantRepository;
import com.lebvil.commerce.venda_api.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant updates) {
        Tenant updated = tenantService.updateTenant(id, updates);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{slug}/status")
    public ResponseEntity<Void> toggleStatus(@PathVariable String slug) {

        Tenant tenant = tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
        LocalDateTime now = LocalDateTime.now();


        tenant.setOpen(!tenant.isOpen());
        tenantRepository.save(tenant);
        return ResponseEntity.ok().build();
    }
}
