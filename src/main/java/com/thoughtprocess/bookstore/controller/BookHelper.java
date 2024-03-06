package com.thoughtprocess.bookstore.controller;

import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import com.thoughtprocess.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Component
public class BookHelper {
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BookHelper(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public void rejectInvalidFields(BindingResult result)
    {
        rejectIfValid(result, "cost", "Price must be valid");
        rejectIfValid(result, "title", "Title must be valid");
        rejectIfValid(result, "author", "Author must be valid");

    }

    public void rejectIfValid(BindingResult result, String fieldName, String message)
    {
        if(result.hasFieldErrors(fieldName))
        {
            result.rejectValue(fieldName, "Error", message);
        }
    }

    public boolean titleExists(String title) {
        return bookService.doesTitleExist(title);
    }

    public void saveBook(BookDTO bookDTO) {
        bookService.save(bookDTO);
    }

    public void updateModelAttributes(Model model) {
        List<BookDTO> books = fetchAllBooks();
        Collections.reverse(books);
        model.addAttribute("books", books);
        model.addAttribute("book", new BookDTO(0d, "", ""));
    }

    public List<BookDTO> fetchAllBooks() {
        return bookService.findAll();
    }
}
