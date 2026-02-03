package com.lebvil.commerce.venda_api.repository;

import com.lebvil.commerce.venda_api.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}
