package com.thoughtprocess.repository;

import org.springframework.data.repository.CrudRepository;
import com.thoughtprocess.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByTitle(String title);
}
