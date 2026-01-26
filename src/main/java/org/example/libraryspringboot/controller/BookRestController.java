package org.example.libraryspringboot.controller;


import org.example.libraryspringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/check-isbn")
    public ResponseEntity<Map<String, Boolean>> checkIsbn(
            @RequestParam String isbn,
            @RequestParam(required = false) Long bookId) {
        boolean exists;

        if (bookId != null) {

            exists = bookService.isIsbnExistsForOtherBook(isbn, bookId);
        } else {
            exists = bookService.isIsbnExists(isbn);
        }

        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("exists", exists);

        return ResponseEntity.ok(reponse);
    }
}
