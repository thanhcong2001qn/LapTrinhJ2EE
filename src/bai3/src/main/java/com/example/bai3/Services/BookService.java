package com.example.bai3.Services;

import com.example.bai3.Models.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    public void addBook(String title, String author) {
        int maxId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);

        Book newBook = new Book(maxId + 1, title, author);
        books.add(newBook);
    }
    public List<Book> getAllBooks() {
        return books;
    }
    public Book getBook(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
    public void updateBook(int id, String title, String author) {
        Book book = getBook(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
        }
    }
}
