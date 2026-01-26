package org.example.libraryspringboot.service;


import jakarta.transaction.Transactional;
import org.example.libraryspringboot.entity.Book;
import org.example.libraryspringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // merri krejt librat prej db
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    public boolean isIsbnExists(String isbn){
        return bookRepository.existsByIsbn(isbn);
    }

    public boolean isIsbnExistsForOtherBook(String isbn, Long bookId){
        if (bookId == null){
            return bookRepository.existsByIsbn(isbn);
        }

        return bookRepository.existsByIsbnAndIdNot(isbn, bookId);
    }

    // nese searchi osht empty i kthen krejt librat
    public List<Book> searchBooks(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()){
            return getAllBooks();
        }
        return bookRepository.searchByTitleOrAuthor(searchTerm.trim());
    }
}
