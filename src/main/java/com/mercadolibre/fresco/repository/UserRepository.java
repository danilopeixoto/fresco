package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u where u.username=:username and u.password=:password ")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUsernameAndCountryHouseId(String username, Long countryHouseId);

    User findByUsername(String username);
}
