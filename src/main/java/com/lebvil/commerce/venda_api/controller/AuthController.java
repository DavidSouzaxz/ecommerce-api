package com.lebvil.commerce.venda_api.controller;

import com.lebvil.commerce.venda_api.entitys.User;
import com.lebvil.commerce.venda_api.repository.UserRepository;
import com.lebvil.commerce.venda_api.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        User user = userRepository.findByEmail(request.get("email"))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(request.get("password"), user.getPassword())) {
            String token = jwtService.generateToken(user.getEmail());
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("tenantSlug", user.getTenant().getSlug());
            
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Senha incorreta");
    }
}