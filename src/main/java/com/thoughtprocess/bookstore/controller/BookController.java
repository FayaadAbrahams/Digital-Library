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
import org.springframework.ui.ModelMap;
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

    @PostMapping("/add-book")
    @ResponseBody
    public String create(@RequestBody BookDTO bookDTO, ModelMap model) {
        model.addAttribute("title", bookDTO.getTitle());
        model.addAttribute("author", bookDTO.getAuthor());
        model.addAttribute("cost", bookDTO.getCost());

        List<BookDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "addbook.html";
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
