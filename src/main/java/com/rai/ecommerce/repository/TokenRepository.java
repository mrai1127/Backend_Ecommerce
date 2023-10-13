package com.rai.ecommerce.repository;

import com.rai.ecommerce.model.AuthenticationToken;
import com.rai.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
}
