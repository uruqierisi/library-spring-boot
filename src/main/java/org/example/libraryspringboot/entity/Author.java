package org.example.libraryspringboot.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;


    // cascade= nese e fshina 1 author edhe librat e tij fshihen
    // orphanRemoval = nese e fshina 1 liber prej listes fshihet edhe prej databazes
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public Author(){

    }
    public Author(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // metoda me shtu liber prej autorit
    public void addBook(Book book){
        books.add(book);
        book.setAuthor(this);
    }

    // metoda me fshi liber
    public void removeBook(Book book){
        books.remove(book);
        book.setAuthor(null);
    }
}
