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

import javax.validation.Valid;
import java.util.List;

//http://localhost:8081/digitalib/books/home
//http://localhost:8081/digitalib/books/add-book
@Controller
@RequestMapping("/digitalib/books")
public class BookController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookHelper bookHelper;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public BookController(BookHelper bookHelper, BookRepository bookRepository, BookService bookService) {
        this.bookHelper = bookHelper;
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
        LOGGER.info("Add Book Page : default book template.");
        // Default State when loading up the page
        model.addAttribute("book", new BookDTO());
        return "addbook";
    }

    @PostMapping("/add-book")
    public String addBookToArchive(@ModelAttribute("book") @Valid BookDTO bookDTO, BindingResult result, Model model) {
        LOGGER.info("Add Book Page : Attempting to add book to the book archive...");
        if (result.hasErrors()) {
            bookHelper.prepareModel(model, "danger", "Form contents are not valid, please retry!");
        } else if (bookHelper.validateBookObject(bookDTO)) {
            String message = bookHelper.isBookTitleUnique(bookDTO) ? "The book title already exists" : "The cost is invalid";
            bookHelper.prepareModel(model, "danger", message);
        } else {
            bookHelper.addBookAndNotifySuccess(bookDTO, model);
        }
        model.addAttribute("books", bookHelper.fetchAllBooks());
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
