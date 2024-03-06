package com.thoughtprocess.database;

import com.thoughtprocess.bookstore.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class DatabaseCleaner {

    private final BookRepository bookRepository;

    public DatabaseCleaner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PreDestroy
    public void cleanDatabase() {
        bookRepository.deleteAll();
    }


}
