package com.code.review.demo.service;

import com.code.review.demo.entity.Author;
import com.code.review.demo.entity.Book;
import com.code.review.demo.repository.AuthorRepository;
import com.code.review.demo.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @PostConstruct
    @Transactional
    public void init() {
        Author author = new Author("Толстой", new HashSet<>());
        Book book1 = new Book("Война и мир", author);
        Book book2 = new Book("Анна Каренина", author);
        author.getBooks().add(book1);
        author.getBooks().add(book2);

        authorRepository.save(author);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Transactional
    public void getById(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id " + id + " not found"));
        author.getBooks();
//        Author author = book.getAuthor();
//
//        Author author1 = new Author();
//        author1.setId(author.getId());
//        author1.setName(author.getName());
//
//        log.info("equals? {}", author1.equals(author));
    }

    public void create() {
        Author author = new Author();
        author.setName("Tolstoy");

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        authorRepository.save(author);

        log.info("Contains? {}", authors.contains(author));
    }
}
