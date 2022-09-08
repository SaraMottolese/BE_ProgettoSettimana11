package com.catalogolibri.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catalogolibri.model.Category;
import com.catalogolibri.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
/*
 * anche in questa classe vale la stessa riflessione che ho fatto per l'inserimento dell'autore. 
 *Ma avendo avuto difficolta' a generarlo nel path dei libri ho scelto di non commentarlo qui in modo da far vedere che funzionano 
 *tutti gli altri metodi collegati 
 **/
@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Aggiunge una categoria", description = "metodo per inserire una nuova categoria nel database. "
			+ "Se il nome della categoria dovesse corrispondere a uno gia' esistente nel database "
			+ "allora verrebbe generato un errore")
	@ApiResponse(responseCode = "200", description = "Categoria inserita correttamente")
	public ResponseEntity<Category> add(@RequestBody Category category) {
		Category addCategory = categoryService.add(category);
		return new ResponseEntity<>(addCategory, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Elimina una categoria", description = "Il metodo ricerca l'autore da eliminare in base all'id"
			+ "Quando lo trova allora lo elimina dal database, altrimenti lancia un errore")
	@ApiResponse(responseCode = "200", description = "Categoria eliminata correttamente")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		categoryService.delete(id);
		return new ResponseEntity<>("Categoria eliminata correttamente", HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary="Aggiornare una categoria", description="Il metodo aggiorno le info di una categoria nel db."
			+ "se l'id dovesse essere presente nel db allora solo il nome della categoria sarebbe aggiornabile, se l'id non venisse trovato"
			+ "allora restituirebbe un errore")
	@ApiResponse(responseCode="200", description="Categoria aggiornata correttamente")
	public ResponseEntity<Category> update(@RequestBody Category category){
		Category updateCategory= categoryService.update(category);
		return new ResponseEntity<Category>(updateCategory, HttpStatus.OK);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista categorie", description = "Il metodo restituisce tutta la lista delle categorie presenti sul database")
	@ApiResponse(responseCode = "200")
	public ResponseEntity<List<Category>> getAll() {
		List<Category> categoriesList = categoryService.findAll();
		if (!categoriesList.isEmpty())
			return new ResponseEntity<>(categoriesList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("getbyname/{name}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary="ricerca per nome", description="Il metodo ricerca la categoria per nome, se non la trova allora lancia un errore")
	public ResponseEntity<Optional<Category>> findByName(@RequestParam String name){
		Optional<Category> findCategory= categoryService.findByName(name);
		return new ResponseEntity<Optional<Category>>(findCategory,HttpStatus.OK);
	}

}
