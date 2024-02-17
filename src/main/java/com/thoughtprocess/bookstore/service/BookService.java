package com.thoughtprocess.bookstore.service;

import com.thoughtprocess.bookstore.model.Book;
import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<BookDTO> findByTitle(String bookTitle) {
        List<Book> books = bookRepo.findByTitle(bookTitle);

        // Simplified to an inline variable return
        return books.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> listBooksByAuthor(String author) {
        List<Book> books = bookRepo.findByAuthor(author);
        // Use Java Streams to map each Book entity to a BookDTO
        return books.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> findAll() {
        List<Book> bookList = (List<Book>) bookRepo.findAll();

        return bookList.stream().map(this::fromBookDTO).collect(Collectors.toList());
    }

    public BookDTO fromBookDTO(Book book) {
        return new BookDTO(book.getCost(), book.getTitle(), book.getAuthor());
    }
}
