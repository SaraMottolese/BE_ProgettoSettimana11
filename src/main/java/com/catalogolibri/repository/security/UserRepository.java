package com.catalogolibri.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalogolibri.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findById(Long id);

	public Optional<User> findByUserName(String userName);

	public boolean existsByEmail(String email);

	public boolean existsByUserName(String userName);
}
