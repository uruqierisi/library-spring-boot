package org.example.libraryspringboot.repository;

import org.example.libraryspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {

    // me kqyr a ekziston ISBN
    boolean existsByIsbn(String isbn);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Book b WHERE b.isbn = :isbn AND b.id != :id")
    boolean existsByIsbnAndIdNot(@Param("isbn") String isbn, @Param("id") Long id);

    // me gjet libren prej ISBN
    Optional<Book> findByIsbn(String isbn);

    // mi gjet librat prej titullit
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> searchByTitle(@Param("title") String title);

    // mi gjet librat prej autorit
    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> searchByAuthorName(@Param("authorName") String authorName);


    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchByTitleOrAuthor(@Param("searchTerm") String searchTerm);
}
