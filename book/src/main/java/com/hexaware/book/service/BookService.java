package com.hexaware.book.service;

import com.hexaware.book.entity.Book;
import com.hexaware.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return getBookRepository().findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return getBookRepository().findById(isbn);
    }

    public Book addBook(Book book) {
        return getBookRepository().save(book);
    }

    public Book updateBook(String isbn, Book updatedBook) {
        return getBookRepository().findById(isbn).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublicationYear(updatedBook.getPublicationYear());
            return getBookRepository().save(book);
        }).orElse(null);
    }

    public boolean deleteBook(String isbn) {
        if (getBookRepository().existsById(isbn)) {
            getBookRepository().deleteById(isbn);
            return true;
        }
        return false;
    }

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
}

