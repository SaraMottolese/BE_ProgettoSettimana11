package com.catalogolibri.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogolibri.exception.BookException;
import com.catalogolibri.model.Author;
import com.catalogolibri.model.Book;
import com.catalogolibri.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	/***************** CRUD *****************/
	public Author add(Author author) {
		Author newAuthor = new Author();
		Optional<Author> authorResult = authorRepository.findByNameAndSurname(author.getName(), author.getSurname());
		// controllo se l'autore esiste, se non esiste lo aggiungo al db se invece
		// esiste lancio l'eccezione
		if (!authorResult.isPresent()) {
			newAuthor.setName(author.getName());
			newAuthor.setSurname(author.getSurname());
			newAuthor.setBooksList(author.getBooksList());
			return authorRepository.save(newAuthor);
		} else
			throw new BookException("Autore gia' inserito nel database");
	}

	public Author update(Author author) {
		Optional<Author> authorResult = authorRepository.findById(author.getId());
		// controllo se l'autore esiste. Se esiste allora lo aggoirno altrimenti lancio
		// l'eccezione
		if (authorResult.isPresent()) {
			Author authorUpdate = authorResult.get();
			authorUpdate.setName(author.getName());
			authorUpdate.setSurname(author.getSurname());
			authorUpdate.setBooksList(author.getBooksList());
			return authorRepository.save(authorUpdate);
		} else
			throw new BookException("Autore non aggiornato!");
	}

	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	public void delete(Long id) {
		Optional<Author> authorResult = authorRepository.findById(id);
		// controllo se l'autore esiste. Se esiste allora lo elimino altrimenti lancio
		// l'eccezione
		if (authorResult.isPresent())
			authorRepository.deleteById(id);
		else
			throw new BookException("Autore non trovato!");
	}

	/***************** FIND BY *****************/

	public Optional<Author> findByNameAndSurname(String name, String surname) {
		return authorRepository.findByNameAndSurname(name, surname);
	}

	public Optional<Author> findById(Long id) {
		return authorRepository.findById(id);
	}

	public Optional<List<Book>> getAuthorBookList(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		Author a = author.get();
		List<Book> bookList = new ArrayList<>();
		for (Book b : a.getBooksList()) {
			bookList.add(b);
		}
			return Optional.ofNullable(bookList);
	}

}
