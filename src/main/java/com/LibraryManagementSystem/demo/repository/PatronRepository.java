package com.LibraryManagementSystem.demo.repository;

import com.LibraryManagementSystem.demo.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}