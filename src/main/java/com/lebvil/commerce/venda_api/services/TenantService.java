package com.lebvil.commerce.venda_api.services;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.repository.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TenantService {

    public final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant findById(Long id) {
        return tenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    public Tenant create(Tenant tenant) {
        // Se um usuário foi informado no corpo, checa se ele já possui uma loja
        if (tenant.getUser() != null && tenant.getUser().getId() != null) {
            Long userId = tenant.getUser().getId();
            if (tenantRepository.existsByUserId(userId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already has a store");
            }
        }

        return tenantRepository.save(tenant);


    }

    public Tenant updateTenant(Long id, Tenant updatedTenant) {
        return tenantRepository.findById(id).map(tenant -> {
            tenant.setName(updatedTenant.getName());
            tenant.setPrimaryColor(updatedTenant.getPrimaryColor());
            tenant.setLogoUrl(updatedTenant.getLogoUrl());
            tenant.setCloseTime(updatedTenant.getCloseTime());
            tenant.setOpenTime(updatedTenant.getOpenTime());
            return tenantRepository.save(tenant);
        }).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }


}
