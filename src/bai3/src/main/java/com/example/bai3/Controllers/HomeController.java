package com.example.bai3.Controllers;

import com.example.bai3.Models.Book;
import com.example.bai3.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class HomeController {
    @Autowired
    private BookService bookService;
    @GetMapping
    public String home(Model model) {
        model.addAttribute("books",bookService.getAllBooks());
        return "book";
    }
    @GetMapping("/add")
    public String addBook() {
        return "add-book";
    }
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable int id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable int id,
                             @RequestParam String title,
                             @RequestParam String author) {
        bookService.updateBook(id, title, author);
        return "redirect:/books";
    }
    @PostMapping("/add")
    public String addBook(@RequestParam String title, @RequestParam String author) {
        bookService.addBook(title, author);
        return "redirect:/books";
    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
