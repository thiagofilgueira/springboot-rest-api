package com.mysample.repository;

import com.mysample.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author thiagofilgueira
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}