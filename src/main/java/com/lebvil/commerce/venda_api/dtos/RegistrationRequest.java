package com.lebvil.commerce.venda_api.dtos;

public record RegistrationRequest(
    String companyName,
    String ownerEmail,
    String password
) {}