package com.hexaware.book;

import com.hexaware.book.entity.Book;
import com.hexaware.book.repository.BookRepository;
import com.hexaware.book.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private Book testBook;

    @BeforeEach
    void setup() {
        testBook = new Book();
        testBook.setIsbn("2024202509");
        testBook.setTitle("Twisted Love");
        testBook.setAuthor("Ana Huang");
        testBook.setPublicationYear(2024);
        bookService.addBook(testBook); 
    }

    @Test
    void testGetAllBooks() {
        var books = bookService.getAllBooks();
        assertFalse(books.isEmpty());
    }

    @Test
    void testGetBookByIsbn() {
        Optional<Book> book = bookService.getBookByIsbn("2024202509");
        assertTrue(book.isPresent());
        assertEquals("Twisted Love", book.get().getTitle());
    }

    @Test
    void testAddBook() {
        Book book = new Book("4567890123", "Java Book", "John Doe", 2025);
        bookService.addBook(book);
        Optional<Book> saved = bookService.getBookByIsbn("4567890123");
        assertTrue(saved.isPresent());
        assertEquals("Java Book", saved.get().getTitle());
    }

    @Test
    void testUpdateBook() {
        Book updated = new Book();
        updated.setTitle("Updated Title");
        updated.setAuthor("Ana Huang");
        updated.setPublicationYear(2024);

        Book result = bookService.updateBook("2024202509", updated);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void testDeleteBook() {
        boolean result = bookService.deleteBook("2024202509");
        assertTrue(result);
        assertFalse(bookService.getBookByIsbn("2024202509").isPresent());
    }

    @AfterEach
    void cleanUp() {
        bookRepository.deleteAll();
    }
}
