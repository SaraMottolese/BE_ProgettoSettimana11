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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catalogolibri.model.Author;
import com.catalogolibri.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/*
 * Dopo una riflessione ho scelto di non far aggiungere autori direttamente dal path dell'autore
 * in quanto non credo che esistano autori che non abbiamo mai scritto un libro.
 * Il metodo e'comunque presenteall'interno di questa classe per far vedere che volendo si potrebbe fare.
 */
@RestController
@RequestMapping("/api/author")
@SecurityRequirement(name = "bearerAuth")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

//	@PostMapping("/add")
//	@Operation(summary = "Aggiunge un autore", description = "metodo per inserire un nuovo autore nel database. "
//			+ "Se il nome e il cognome dell'autore inseriti dovessero corrispondere a uno gia' esistente nel database "
//			+ "allora verrebbe generato un errore")
//	@ApiResponse(responseCode = "200", description = "Autore inserito correttamente")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public ResponseEntity<Author> add(@RequestBody Author author) {
//		Author addAuthor = authorService.add(author);
//		return new ResponseEntity<>(addAuthor, HttpStatus.OK);
//	}
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Elimina un autore", description = "Il metodo ricerca l'autore da eliminare in base all'id. "
			+ "Quando lo trova allora lo elimina dal database, altrimenti lancia un errore")
	@ApiResponse(responseCode = "200", description = "Autore eliminato correttamente")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		authorService.delete(id);
		return new ResponseEntity<>("Autore eliminato correttamente", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Aggiornare un autore", description = "metodo per aggiornare le info di un autore nel database. "
			+ "Se l'id dovesse corrispondere ad uno presente nel database allora l'autore verrebba aggiornato (e' possibile aggiornare tutti i campi tranne l'id)")
	@ApiResponse(responseCode = "200", description = "Autore aggiornato correttamente")
	public ResponseEntity<Author> update(@RequestBody Author author) {
		Author updateAuthor = authorService.update(author);
		return new ResponseEntity<>(updateAuthor, HttpStatus.OK);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista autori", description = "Il metodo restituisce tutta la lista degli autori presenti nel db")
	@ApiResponse(responseCode = "200")
	public ResponseEntity<List<Author>> getAll() {
		List<Author> authorsList = authorService.findAll();
		if (!authorsList.isEmpty())
			return new ResponseEntity<>(authorsList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/getByNameAndSurname")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "ricerca per nome e cognome")
	@ApiResponse(responseCode = "200", description = "Autore trovato per nome e cognome")
	public ResponseEntity<Optional<Author>> findByNameAndSurnameDue(@RequestParam String name, @RequestParam String surname) {
		Optional<Author> findAuthor= authorService.findByNameAndSurname(name, surname);
		return new ResponseEntity<>(findAuthor, HttpStatus.OK);
	}

}
