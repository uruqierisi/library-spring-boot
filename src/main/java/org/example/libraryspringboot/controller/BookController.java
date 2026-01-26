package org.example.libraryspringboot.controller;


import jakarta.validation.Valid;
import org.example.libraryspringboot.entity.Book;
import org.example.libraryspringboot.service.AuthorService;
import org.example.libraryspringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/app/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listBooks(@RequestParam(required = false) String search, Model model){
        List<Book> books;

        if (search != null && !search.trim().isEmpty()){
            books = bookService.searchBooks(search);
            model.addAttribute("search", search);
        } else{
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        return "book/index";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());

        model.addAttribute("authors", authorService.getAllAuthors());

        return "book/form";
    }

    @PostMapping
    public String createBook(@Valid @ModelAttribute Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        // Kontrollo nëse ISBN ekziston
        if (bookService.isIsbnExists(book.getIsbn())) {
            result.rejectValue("isbn", "error.book", "ISBN already exists");
        }

        if (result.hasErrors()){
            model.addAttribute("authors", authorService.getAllAuthors());
            return "book/form";
        }

        bookService.saveBook(book);

        redirectAttributes.addFlashAttribute("success", "Book created successfully!");

        return "redirect:/app/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Book book = bookService.getBookById(id);

        if (book == null){
            return "redirect:/app/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());

        return "book/form";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute Book book,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        book.setId(id);

        if(result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return  "book/form";
        }

        bookService.saveBook(book);

        redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
        return  "redirect:/app/books";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes){
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("success", "Book deleted successfully!");
        return "redirect:/app/books";
    }


}
