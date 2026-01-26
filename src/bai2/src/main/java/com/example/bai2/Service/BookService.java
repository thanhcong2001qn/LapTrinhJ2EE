package com.example.bai2.Service;

import com.example.bai2.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>() {{
        add(new Book(1, "Dế Mèn Phiêu Lưu Ký", "Tô Hoài"));
        add(new Book(2, "Số Đỏ", "Vũ Trọng Phụng"));
        add(new Book(3, "Lão Hạc", "Nam Cao"));
        add(new Book(4, "Chí Phèo", "Nam Cao"));
        add(new Book(5, "Tắt Đèn", "Ngô Tất Tố"));
        add(new Book(6, "Vợ Nhặt", "Kim Lân"));
    }};

    public List<Book> getAllBooks() {
        return books;
    }
    public void addBook(Book book) {
        books.add(book);
    }
    public void removeBook(Book book) {
        books.remove(book);
    }
    public Book getBook(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public void updateBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return;
            }
        }
    }
}
