package com.catalogolibri.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.catalogolibri.model.Book;
import com.catalogolibri.service.AuthorService;
import com.catalogolibri.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Aggiunge un libro", description = "metodo per inserire un nuovo libro nel db."
			+ "se il titolo e' gia' presente nel db allora verra' lanciato un errore")
	@ApiResponse(responseCode = "200", description = "Libro aggiunto correttamente")
	public ResponseEntity<Book> add(@RequestBody Book book) {
		Book addBook = bookService.add(book);
		return new ResponseEntity<>(addBook, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Elimina un libro", description = "Il metodo controlla se l'id del libro e' presente nel db. "
			+ "Se lo trova elimina il libro altrimenti lancia un errore")
	@ApiResponse(responseCode = "200", description = "Libro eliminato correttamente!")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		bookService.delete(id);
		return new ResponseEntity<String>("Libro eliminato correttamente", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Aggiorna un libro", description = "Il metodo controlla se l'id del libro e' presente nel db. "
			+ "Se lo trova aggiorna i record altrimenti lancia un errore")
	@ApiResponse(responseCode = "200", description = "Libro aggiornato correttamente!")
	public ResponseEntity<Book> update(@RequestBody Book book) {
		Book updateBook = bookService.update(book);
		return new ResponseEntity<Book>(updateBook, HttpStatus.OK);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "lista libri", description = "Il metodo restituisce tutti i libri presenti nel db.")
	@ApiResponse(responseCode = "200")
	public ResponseEntity<List<Book>> getAll() {
		List<Book> booksList = bookService.findAll();
		if (!booksList.isEmpty())
			return new ResponseEntity<>(booksList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/title")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary="ricerca per titolo")
	@ApiResponse(responseCode="200")
	public ResponseEntity<Optional<Book>> findByTitle(@RequestParam String title){
		Optional<Book> resultBook= bookService.findByTitle(title);
		return new ResponseEntity<Optional<Book>>(resultBook, HttpStatus.OK);
	}
	
//	@GetMapping("/author/{id}")
//	@Operation(summary="ricerca per autore")
//	@ApiResponse(responseCode="200")
//	public ResponseEntity<List<Book>> findByAuthor(@PathVariable Long id){
//		List<Book> bookList=bookService.findByAuthor(id);
//		return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
//		if(!bookList.isEmpty())
//			return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//	}
	
	@GetMapping("/author/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary="ricerca per autore")
	@ApiResponse(responseCode="200")
	public ResponseEntity<List<Book>> findByAuthor(@PathVariable Long id){
		Optional<List<Book>>booksList= authorService.getAuthorBookList(id);
		List<Book> bl=booksList.get();
		return new ResponseEntity<List<Book>>(bl,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categories}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary="ricerca per categoria")
	@ApiResponse(responseCode="200")
	public ResponseEntity<List<Book>> findByCategory(@PathVariable Set<Long> categories){
		List<Book> bookList=bookService.findByCategory(categories);
		if(!bookList.isEmpty())
			return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

}
