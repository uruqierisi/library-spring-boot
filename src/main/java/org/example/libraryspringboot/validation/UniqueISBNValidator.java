package org.example.libraryspringboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.libraryspringboot.repository.BookRepository;
import org.springframework.stereotype.Component;

public class UniqueISBNValidator implements ConstraintValidator<UniqueISBN, String> {

    private final BookRepository bookRepository;

    public UniqueISBNValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return true;
        }

        return !bookRepository.existsByIsbn(isbn);
    }
}