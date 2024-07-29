package com.LibraryManagementSystem.demo.controller;

import com.LibraryManagementSystem.demo.entity.BorrowingRecord;
import com.LibraryManagementSystem.demo.security.JwtRequestFilter;
import com.LibraryManagementSystem.demo.service.BorrowingRecordService;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BorrowingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class BorrowingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BorrowingRecordService borrowingRecordService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

        @Test
    public void shouldBorrowBook() throws Exception {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBorrowDate(LocalDate.now());

        given(borrowingRecordService.borrowBook(anyLong(), anyLong())).willReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBook() throws Exception {
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setReturnDate(LocalDate.now());

        given(borrowingRecordService.returnBook(anyLong(), anyLong())).willReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
