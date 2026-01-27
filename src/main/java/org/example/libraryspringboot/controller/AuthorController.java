package org.example.libraryspringboot.controller;

import jakarta.validation.Valid;
import org.example.libraryspringboot.entity.Author;
import org.example.libraryspringboot.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import java.util.List;

@Controller
@RequestMapping("/app/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model){
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "author/index";
    }


    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model){
        model.addAttribute("author", new Author());
        return "author/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createAuthor(@Valid @ModelAttribute Author author,
                               BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            return "author/form";
        }

        authorService.saveAuthor(author);
        redirectAttributes.addFlashAttribute("success", "Author created successfully!");
        return "redirect:/app/authors";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model){
        Author author = authorService.getAuthorById(id);

        if (author == null) {
            return "redirect:/app/authors";
        }

        model.addAttribute("author", author);
        return "author/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAuthor(@PathVariable Long id, @Valid
                               @ModelAttribute Author author, BindingResult result,
                               RedirectAttributes redirectAttributes){
        author.setId(id);

        if (result.hasErrors()){
            return  "author/form";
        }

        authorService.saveAuthor(author);
        redirectAttributes.addFlashAttribute("success", "Author updated successfully!");
        return "redirect:/app/authors";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes){
        authorService.deleteAuthor(id);
        redirectAttributes.addFlashAttribute("success", "Author deleted successfully!");
        return "redirect:/app/authors";
    }
}
