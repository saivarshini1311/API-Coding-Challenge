package com.hexaware.book.controller;

import com.hexaware.book.entity.Book;
import com.hexaware.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(isbn, updatedBook);
        if (book != null)
            return ResponseEntity.ok(book);
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable String isbn) {
        boolean deleted = bookService.deleteBook(isbn);
        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Book not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
