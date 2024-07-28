package com.LibraryManagementSystem.demo.service;

import com.LibraryManagementSystem.demo.entity.Book;
import com.LibraryManagementSystem.demo.entity.BorrowingRecord;
import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.exception.ResourceNotFoundException;
import com.LibraryManagementSystem.demo.repository.BookRepository;
import com.LibraryManagementSystem.demo.repository.BorrowingRecordRepository;
import com.LibraryManagementSystem.demo.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id " + patronId));

        // Check if there is an active borrowing record for this book and patron
        Optional<BorrowingRecord> activeRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        if (activeRecord.isPresent()) {
            throw new IllegalStateException("This book is already borrowed by the patron and not yet returned.");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("No active borrowing record found for book id " + bookId + " and patron id " + patronId));

        record.setReturnDate(LocalDate.now());

        return borrowingRecordRepository.save(record);
    }
}