package com.LibraryManagementSystem.demo.service;

import com.LibraryManagementSystem.demo.entity.Book;
import com.LibraryManagementSystem.demo.entity.BorrowingRecord;
import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.exception.ResourceNotFoundException;
import com.LibraryManagementSystem.demo.repository.BookRepository;
import com.LibraryManagementSystem.demo.repository.BorrowingRecordRepository;
import com.LibraryManagementSystem.demo.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @Test
    public void shouldBorrowBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepository.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId())).thenReturn(Optional.empty());
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.borrowBook(book.getId(), patron.getId());

        assertNotNull(result);
        assertEquals(book.getId(), result.getBook().getId());
        assertEquals(patron.getId(), result.getPatron().getId());
        assertEquals(LocalDate.now(), result.getBorrowDate());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFound() {
        Long bookId = 1L;
        Long patronId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.borrowBook(bookId, patronId));
    }

    @Test
    public void shouldThrowExceptionWhenPatronNotFound() {
        Book book = new Book();
        book.setId(1L);

        Long patronId = 1L;

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.borrowBook(book.getId(), patronId));
    }

    @Test
    public void shouldThrowExceptionWhenBookAlreadyBorrowed() {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord borrowingRecord = new BorrowingRecord();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(patronRepository.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId())).thenReturn(Optional.of(borrowingRecord));

        assertThrows(IllegalStateException.class, () -> borrowingRecordService.borrowBook(book.getId(), patron.getId()));
    }

    @Test
    public void shouldReturnBook() {
        Book book = new Book();
        book.setId(1L);

        Patron patron = new Patron();
        patron.setId(1L);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId())).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.returnBook(book.getId(), patron.getId());

        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getReturnDate());
    }

    @Test
    public void shouldThrowExceptionWhenNoActiveBorrowingRecordFound() {
        Long bookId = 1L;
        Long patronId = 1L;

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowingRecordService.returnBook(bookId, patronId));
    }
}
