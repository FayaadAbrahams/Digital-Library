package com.thoughtprocess.bookstore.service;

import com.sun.xml.bind.v2.TODO;
import com.thoughtprocess.bookstore.model.Book;
import com.thoughtprocess.bookstore.model.apiresponse.BookResponse;
import com.thoughtprocess.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo)
    {
        this.bookRepo = bookRepo;
    }

    public List<BookResponse> findByTitle(String bookTitle)
    {
        List<Book> book = bookRepo.findByTitle(bookTitle);

        // TODO: Figure out how to use the services correctly
        return null;
    }

}
