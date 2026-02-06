package com.lebvil.commerce.venda_api.services;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    public final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant findById(Long id) {
        return tenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    public Tenant updateTenant(Long id, Tenant updatedTenant) {
        return tenantRepository.findById(id).map(tenant -> {
            tenant.setName(updatedTenant.getName());
            tenant.setPrimaryColor(updatedTenant.getPrimaryColor());
            tenant.setLogoUrl(updatedTenant.getLogoUrl());
            return tenantRepository.save(tenant);
        }).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }


}
