package com.LibraryManagementSystem.demo.controller;

import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.security.JwtRequestFilter;
import com.LibraryManagementSystem.demo.service.PatronService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatronController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PatronService patronService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;
        @Test
    public void shouldReturnListOfPatrons() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        List<Patron> patrons = Arrays.asList(patron);

        given(patronService.getAllPatrons()).willReturn(patrons);

        mockMvc.perform(get("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(patron.getName()));
    }
        @Test
    public void shouldReturnPatronById() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        given(patronService.getPatronById(anyLong())).willReturn(patron);

        mockMvc.perform(get("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void shouldCreatePatron() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        given(patronService.addPatron(any(Patron.class))).willReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"contactInformation\": \"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void shouldUpdatePatron() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        given(patronService.updatePatron(anyLong(), any(Patron.class))).willReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"contactInformation\": \"jane.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void shouldDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
