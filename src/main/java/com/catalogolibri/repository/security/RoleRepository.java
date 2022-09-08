package com.catalogolibri.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalogolibri.model.Role;
import com.catalogolibri.model.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(Roles role);
}
