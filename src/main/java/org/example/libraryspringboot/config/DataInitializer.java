package org.example.libraryspringboot.config;

import org.example.libraryspringboot.entity.Author;
import org.example.libraryspringboot.entity.Book;
import org.example.libraryspringboot.entity.User;
import org.example.libraryspringboot.repository.AuthorRepository;
import org.example.libraryspringboot.repository.BookRepository;
import org.example.libraryspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole("ROLE_USER");
            userRepository.save(user);

            System.out.println(" Created test users: admin/admin and user/user");
        }

        if (authorRepository.count() == 0) {
            Author author1 = new Author("Stjero Spasse");
            Author author2 = new Author("Dritoro Agolli");
            Author author3 =  new Author("Ismail Kadare");

            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);

            Book book1 = new Book("Pse? ", "902342348", "Drama", author1);
            Book book2 = new Book("Deshtaku","12345567","Drama", author2);
            Book book3 = new Book("Pallati i endrrave", "123456345", "Fantasy", author3);


            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

            System.out.println("Created sample books and authors");
        }
    }
}
