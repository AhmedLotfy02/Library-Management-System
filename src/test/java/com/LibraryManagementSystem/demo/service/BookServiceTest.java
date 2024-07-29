package com.LibraryManagementSystem.demo.service;

import com.LibraryManagementSystem.demo.entity.Book;
import com.LibraryManagementSystem.demo.exception.ResourceNotFoundException;
import com.LibraryManagementSystem.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldReturnAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setIsbn("9780743273565");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("To Kill a Mockingbird");
        book2.setAuthor("Harper Lee");
        book2.setIsbn("9780061120084");

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
    }

    @Test
    public void shouldReturnBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setIsbn("9780743273565");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(book.getId());

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenBookNotFoundById() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    public void shouldAddBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setIsbn("9780743273565");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    public void shouldUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setIsbn("9780743273565");

        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("Updated Title");
        updatedBookDetails.setAuthor("Updated Author");
        updatedBookDetails.setIsbn("1234567890");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBookDetails);

        Book result = bookService.updateBook(book.getId(), updatedBookDetails);

        assertNotNull(result);
        assertEquals(updatedBookDetails.getTitle(), result.getTitle());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistingBook() {
        Long bookId = 1L;
        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("Updated Title");
        updatedBookDetails.setAuthor("Updated Author");
        updatedBookDetails.setIsbn("1234567890");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(bookId, updatedBookDetails));
    }

    @Test
    public void shouldDeleteBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setIsbn("9780743273565");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.deleteBook(book.getId());

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistingBook() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(bookId));
    }
}
