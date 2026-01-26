package com.example.bai2.Controller;


import com.example.bai2.Service.BookService;
import com.example.bai2.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    public BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }
    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) {
        return bookService.getBook(id);
    }
    @DeleteMapping("/remove")
    public Book removeBook(@RequestBody Book book) {
        bookService.removeBook(book);
        return book;
    }
    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return book;
    }
}
