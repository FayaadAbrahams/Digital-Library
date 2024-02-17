package com.thoughtprocess.bookstore.controller;

import com.thoughtprocess.bookstore.model.Book;
import com.thoughtprocess.bookstore.model.dto.BookDTO;
import com.thoughtprocess.bookstore.repository.BookRepository;
import com.thoughtprocess.bookstore.service.BookService;
import com.thoughtprocess.exception.BookIdMismatchException;
import com.thoughtprocess.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/digitalib/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    @ResponseBody
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }
    @GetMapping("/home")
    public String homePage(Model model) {
        List<BookDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "home";
    }


    @GetMapping("/getByAuthor/{bookAuthor}")
    public List<BookDTO> listBooksByAuthor(@PathVariable String bookAuthor) {
        return bookService.listBooksByAuthor(bookAuthor);
    }

    @GetMapping("/findByTitle/{bookTitle}")
    public List<BookDTO> findByTitle(@PathVariable String bookTitle) {
        return bookService.findByTitle(bookTitle);
    }

    @GetMapping("/findOne/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book createdBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
