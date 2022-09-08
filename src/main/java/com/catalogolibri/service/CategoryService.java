package com.catalogolibri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogolibri.exception.BookException;
import com.catalogolibri.model.Category;
import com.catalogolibri.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/***************** CRUD *****************/
	public Category add(Category category) {
		Category newCategory = new Category();
		Optional<Category> categoryResult = categoryRepository.findByName(category.getName());
		if (!categoryResult.isPresent()) {
			newCategory.setName(category.getName());
			newCategory.setBooksList(category.getBooksList());
			return categoryRepository.save(newCategory);
		} else
			throw new BookException("Categoria gia' inserita nel database");
	}

	public Category update(Category category) {
		Optional<Category> categoryResult = categoryRepository.findById(category.getId());
		if (categoryResult.isPresent()) {
			Category categoryUpdate = categoryResult.get();
			categoryUpdate.setName(category.getName());
			return categoryRepository.save(categoryUpdate);
		} else
			throw new BookException("Categoria non aggiornata!");
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public void delete(Long id) {
		Optional<Category> categoryResult = categoryRepository.findById(id);
		if(categoryResult.isPresent())
		categoryRepository.deleteById(id);
		else
			throw new BookException("Categoria non trovato!");
	}

	/***************** FIND BY *****************/

	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	public Optional<Category> findByName(String name) {
		return categoryRepository.findByName(name);
	}

}
