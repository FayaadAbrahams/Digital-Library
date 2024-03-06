package com.thoughtprocess.bookstore.repository;


import com.thoughtprocess.bookstore.model.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    @NotNull
    List<Book> findAll();
    boolean existsByTitle(String title);

}
