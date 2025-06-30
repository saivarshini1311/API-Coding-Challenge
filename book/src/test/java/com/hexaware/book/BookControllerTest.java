package com.hexaware.book;


import com.hexaware.book.entity.Book;
import com.hexaware.book.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    private Book testBook;

    @BeforeEach
    void setup() {
        testBook = new Book("1111222233", "Test REST", "Author", 2024);
        bookRepository.save(testBook);
    }

    @AfterEach
    void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    void testGetAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("/api/books", Book[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void testGetBookByIsbn() {
        ResponseEntity<Book> response = restTemplate.getForEntity("/api/books/1111222233", Book.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test REST", response.getBody().getTitle());
    }

    @Test
    void testAddBook() {
        Book newBook = new Book("2222333344", "New Book", "Diya", 2025);

        ResponseEntity<Book> response = restTemplate.postForEntity("/api/books", newBook, Book.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Book created = response.getBody();
        assertNotNull(created);
        assertEquals("New Book", created.getTitle());
    }

    @Test
    void testUpdateBook() {
        Book updated = new Book();
        updated.setTitle("Updated Title");
        updated.setAuthor("Author");
        updated.setPublicationYear(2025);

        HttpEntity<Book> request = new HttpEntity<>(updated);
        ResponseEntity<Book> response = restTemplate.exchange(
                "/api/books/1111222233", HttpMethod.PUT, request, Book.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    void testDeleteBook() {
        restTemplate.delete("/api/books/1111222233");
        assertFalse(bookRepository.findById("1111222233").isPresent());
    }
}

