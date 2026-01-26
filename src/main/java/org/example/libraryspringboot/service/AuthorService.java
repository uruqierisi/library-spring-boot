package org.example.libraryspringboot.service;


import jakarta.transaction.Transactional;
import org.example.libraryspringboot.entity.Author;
import org.example.libraryspringboot.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }
}
