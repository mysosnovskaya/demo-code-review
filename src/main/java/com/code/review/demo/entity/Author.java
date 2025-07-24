package com.code.review.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    public Author(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.getId()) && Objects.equals(name, author.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
