package com.thoughtprocess.bookstore.controller;

import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import com.thoughtprocess.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

@Component
public class BookHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(BookHelper.class);
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BookHelper(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public boolean validateBookObject(BookDTO bookDTO) {
        if (!isBookTitleUnique(bookDTO)) {
            LOGGER.warn("Book Helper : Book was not added to the book archive due to 'Title' already existing -> " + bookDTO.getTitle());
            return false;
        } else if (!isCostValid(bookDTO)) {
            LOGGER.warn("Book Helper : Book was not added to the book archive due 'Cost' being invalid -> " + bookDTO.getCost());
            return false;
        } else {
            LOGGER.info("Book Helper : Book is valid!");
            return true;
        }
    }

    public void prepareModel(Model model, String statusType, String statusMessage) {
        model.addAttribute("messageType", statusType);
        model.addAttribute("message", statusMessage);
    }

    public boolean isBookTitleUnique(BookDTO bookDTO) {
        return bookService.findByTitle(bookDTO.getTitle()).isEmpty();
    }

    public boolean isCostValid(BookDTO bookDTO) {
        Double cost = bookDTO.getCost();
        try {
            Double.parseDouble(cost.toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return cost >= 0.01 && cost <= 999.99;
    }

    public void addBookAndNotifySuccess(BookDTO bookDTO, Model model) {
        saveBook(bookDTO);
        prepareModel(model, "success", "Added book successfully!");
        updateModelAttributes(model);
    }


    public void saveBook(BookDTO bookDTO) {
        try {
            bookService.save(bookDTO);
            LOGGER.info("Add Book Page : Book was added successfully to the book archive.");
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Add Book Page : Book was unable to be added. " + e.getMessage());
        }
    }

    public void updateModelAttributes(Model model) {
        List<BookDTO> books = fetchAllBooks();
        Collections.reverse(books);
        model.addAttribute("books", books);
        model.addAttribute("book", new BookDTO(0d, "", ""));
    }

    public List<BookDTO> fetchAllBooks() {
        List<BookDTO> books = bookService.findAll();
        Collections.reverse(books);
        return books;
    }
}
