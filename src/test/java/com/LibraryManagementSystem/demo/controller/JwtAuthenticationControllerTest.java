package com.LibraryManagementSystem.demo.controller;

import com.LibraryManagementSystem.demo.model.JwtRequest;
import com.LibraryManagementSystem.demo.service.UserDetailsServiceImpl;
import com.LibraryManagementSystem.demo.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JwtAuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void shouldAuthenticateAndReturnToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("admin");
        jwtRequest.setPassword("password");

        String token = "mockedToken";

        given(userDetailsService.loadUserByUsername("admin"))
                .willReturn(Mockito.mock(org.springframework.security.core.userdetails.UserDetails.class));
        given(jwtTokenUtil.generateToken(ArgumentMatchers.any(org.springframework.security.core.userdetails.UserDetails.class)))
                .willReturn(token);

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }
}