package com.mysample.repository;

import com.mysample.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author thiagofilgueira
 */
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}