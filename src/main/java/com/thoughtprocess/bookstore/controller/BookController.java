package com.thoughtprocess.bookstore.controller;

import com.thoughtprocess.bookstore.model.Book;
import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import com.thoughtprocess.bookstore.service.BookService;
import com.thoughtprocess.exception.BookIdMismatchException;
import com.thoughtprocess.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

import java.util.List;

//http://localhost:8081/digitalib/books/home
//http://localhost:8081/digitalib/books/add-book
@Controller
@RequestMapping("/digitalib/books")
public class BookController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        LOGGER.info("Home Page : Fetching all books from book repository.");
        List<BookDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "home";
    }

    @GetMapping("/get-by-author/{bookAuthor}")
    public List<BookDTO> listBooksByAuthor(@PathVariable String bookAuthor) {
        return bookService.listBooksByAuthor(bookAuthor);
    }

    @GetMapping("/find-by-title/{bookTitle}")
    public List<BookDTO> findByTitle(@PathVariable String bookTitle) {
        return bookService.findByTitle(bookTitle);
    }

    @GetMapping("/find-one/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/add-book")
    public String addBookPage(Model model) {
        LOGGER.info("Add Book Page : book template.");
        // Default State when loading up the page
        BookDTO bookDTO = new BookDTO(0.00, "", "");
        model.addAttribute("book", bookDTO);
        return "addbook";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        LOGGER.info("Add Book Page : attempting to add book to the book repository.");

        if (result.hasErrors()) {
            LOGGER.warn("Add Book Page : book was not added to the book repository.");
            return "addbook";
        } else if (bookService.doesTitleExist(bookDTO.getTitle())) {
            LOGGER.warn("Add Book Page : book was not added, already exists.");
            return "addbook";
        }
        bookService.save(bookDTO);
        LOGGER.info("Add Book Page : book was successfully added to the book repository.");
        //TODO IA: 05 Mar :: Optimize the code, so that methods are less bulky (OOP)
        List<BookDTO> books = bookService.findAll();
        // Return the list in reversed order
        Collections.reverse(books);
        model.addAttribute("books", books);
        model.addAttribute("book", new BookDTO(0d, "",""));
        return "addbook";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/update-book/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
