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
import org.springframework.web.bind.annotation.*;

import java.util.List;
//http://localhost:8081/digitalib/books/home
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

      BookDTO bookDTO = new BookDTO(0.0d, "", "");
        model.addAttribute("book", bookDTO);
        return "addbook";
    }

  @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") BookDTO book) {
      LOGGER.info("Add Book Page : attempting to add book to the book repository.");

      LOGGER.info("Add Book Page : book was successfully added to the book repository.");
//      TODO IA: 29 Feb :: please return all books in desc order, also map it to the add-book html
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
