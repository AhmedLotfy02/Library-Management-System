package com.LibraryManagementSystem.demo.controller;

import com.LibraryManagementSystem.demo.entity.Book;
import com.LibraryManagementSystem.demo.security.JwtRequestFilter;
import com.LibraryManagementSystem.demo.service.BookService;
import com.LibraryManagementSystem.demo.service.UserDetailsServiceImpl;
import com.LibraryManagementSystem.demo.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Test
    public void testGetAllBooks() throws Exception {
        Book book= new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setIsbn("9780743273565");
        when(bookService.getBookById(book.getId())).thenReturn(book);
        // Act
        ResultActions response = mockMvc.perform(get("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON));
        // Assert
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(book)));

    }
    @Test
    public void shouldReturnListOfBooks() throws Exception {
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

        when(bookService.getAllBooks()).thenReturn(books);

        ResultActions response = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(books)));
    }
        @Test
    public void shouldCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Book Author");
        book.setPublicationYear(2020);
        book.setIsbn("1234567890");

        given(bookService.addBook(any(Book.class))).willReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Book Title\", \"author\": \"Book Author\", \"publicationYear\": 2020, \"isbn\": \"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }
        @Test
    public void shouldUpdateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Book Author");
        book.setPublicationYear(2020);
        book.setIsbn("1234567890");

        given(bookService.updateBook(anyLong(), any(Book.class))).willReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"publicationYear\": 2021, \"isbn\": \"0987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    }
