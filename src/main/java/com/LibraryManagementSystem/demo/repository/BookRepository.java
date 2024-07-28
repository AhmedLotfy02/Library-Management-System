package com.LibraryManagementSystem.demo.repository;

import com.LibraryManagementSystem.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}