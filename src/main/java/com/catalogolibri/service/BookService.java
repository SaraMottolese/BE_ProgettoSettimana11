package com.catalogolibri.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogolibri.exception.BookException;
import com.catalogolibri.model.Author;
import com.catalogolibri.model.Book;
import com.catalogolibri.model.Category;
import com.catalogolibri.repository.AuthorRepository;
import com.catalogolibri.repository.BookRepository;
import com.catalogolibri.repository.CategoryRepository;

/*
 * quando vado ad inserire un nuovo libro, non posso inserire le categorie. Purtroppo mi da un errore nella servlet 
 * che non riesco a capire da cosa derivi e quindi anche come poterlo risolvere
 */
@Service
public class BookService {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/***************** CRUD *****************/
	public Book add(Book book) {
		Book newBook = new Book();
		Optional<Book> bookResult = bookRepository.findByTitle(book.getTitle());
		// controllo se il titolo eee1 gia' presente nel db, se non c'e' lo aggiungo
		// altrimenti lancio un'eccezione
		if (!bookResult.isPresent()) {
			newBook.setAuthors(book.getAuthors());
			newBook.setCategories(book.getCategories());
			newBook.setPrice(book.getPrice());
			newBook.setPublicationDate(book.getPublicationDate());
			newBook.setTitle(book.getTitle());
			return bookRepository.save(newBook);
		} else
			throw new BookException("Libro gia' esistente nel db");
	}

	public Book update(Book book) {
		Optional<Book> bookResult = bookRepository.findById(book.getId());
		if (bookResult.isPresent()) {
			Book bookUpdate = bookResult.get();
			bookUpdate.setAuthors(book.getAuthors());
			bookUpdate.setCategories(book.getCategories());
			bookUpdate.setPrice(book.getPrice());
			bookUpdate.setPublicationDate(book.getPublicationDate());
			bookUpdate.setTitle(book.getTitle());
			return bookRepository.save(bookUpdate);
		} else
			throw new BookException("Libro non aggiornato!");
	}

	public void delete(Long id) {
		Optional<Book> bookResult = bookRepository.findById(id);
		if (bookResult.isPresent())
			bookRepository.deleteById(id);
		else
			throw new BookException("Libro non trovato");
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	/***************** FIND BY *****************/

	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}

	public Optional<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	public List<Book> findByAuthor(Long id) {
		List<Book> booksList = new ArrayList();
		;
		Optional<Author> author = authorRepository.findById(id);
		if (author.isPresent()) {
			Author authorResult = author.get();
			booksList = authorResult.getBooksList();
		}
		return booksList;
	}

	public List<Book> findByCategory(Set<Long> categories) {
		List<Book> booksList = new ArrayList<>();
		List<Category> categoryList = categoryRepository.findAllById(categories);
		if (categoryList.isEmpty())
			throw new BookException("Nessun Libro e' stato trovato per questa categria");
		else {
			for (Book b : findAll()) {
				for (Author c : b.getAuthors()) {
					if (categoryList.contains(c))
						booksList.add(b);
				}
			}
		}
		return booksList;
	}
}
