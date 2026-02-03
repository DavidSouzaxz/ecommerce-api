package com.lebvil.commerce.venda_api.services;

import com.lebvil.commerce.venda_api.entitys.Tenant;
import com.lebvil.commerce.venda_api.entitys.User;
import com.lebvil.commerce.venda_api.repository.TenantRepository;
import com.lebvil.commerce.venda_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Garante que existe um Tenant (Loja) para o admin
        if (tenantRepository.count() == 0) {
            Tenant tenant = new Tenant();
            tenant.setName("Lebvil Burger");
            tenant.setSlug("lebvil-burger");
            tenant.setPrimaryColor("#e63946");
            tenantRepository.save(tenant);
        }


        if (userRepository.count() == 0) {
            User admin = new User();

            admin.setEmail("root@root.com");
            admin.setRole("ROLE_ADMIN");
            admin.setPassword(passwordEncoder.encode("123456"));
            

            Tenant defaultTenant = tenantRepository.findAll().get(0);
            admin.setTenant(defaultTenant);

            userRepository.save(admin);
            System.out.println(">>> Usuário Admin criado: admin@teste.com / 123456");
        }
    }
}