package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
