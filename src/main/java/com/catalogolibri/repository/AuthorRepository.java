package com.catalogolibri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.catalogolibri.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {


	@Query("SELECT a FROM Author a WHERE a.name= :name and a.surname= :surname")
	Optional<Author> findByNameAndSurname(String name, String surname);
}
