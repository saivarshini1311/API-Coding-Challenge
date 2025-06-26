package com.hexaware.book.service;

import com.hexaware.book.entity.Book;
import com.hexaware.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setup() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService();
        bookService = spy(bookService);
        bookService.setBookRepository(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(new Book(), new Book()));
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void testGetBookByIsbn() {
        Book book = new Book();
        book.setIsbn("123");
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        assertTrue(bookService.getBookByIsbn("123").isPresent());
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setIsbn("456");
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals("456", bookService.addBook(book).getIsbn());
    }

    @Test
    void testUpdateBook() {
        Book existing = new Book();
        existing.setIsbn("789");
        existing.setTitle("Old Title");

        Book updated = new Book();
        updated.setTitle("New Title");

        when(bookRepository.findById("789")).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenReturn(existing);

        Book result = bookService.updateBook("789", updated);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById("101")).thenReturn(true);
        boolean result = bookService.deleteBook("101");
        assertTrue(result);
        verify(bookRepository, times(1)).deleteById("101");
    }
}