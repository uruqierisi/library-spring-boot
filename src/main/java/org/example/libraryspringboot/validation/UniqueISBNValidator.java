package org.example.libraryspringboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.libraryspringboot.repository.BookRepository;
import org.springframework.stereotype.Component;

public class UniqueISBNValidator implements ConstraintValidator<UniqueISBN, String> {

    private final BookRepository bookRepository;

    // Constructor me BookRepository - Spring e injekton automatikisht
    public UniqueISBNValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        // Nëse ISBN është null ose bosh, lëre @NotBlank ta validojë
        if (isbn == null || isbn.trim().isEmpty()) {
            return true;
        }

        // Kontrollo nëse ISBN ekziston në databazë
        return !bookRepository.existsByIsbn(isbn);
    }
}