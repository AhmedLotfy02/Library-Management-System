package com.LibraryManagementSystem.demo.security;

import com.LibraryManagementSystem.demo.service.UserDetailsServiceImpl;
import com.LibraryManagementSystem.demo.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter();
        jwtRequestFilter.userDetailsService = userDetailsService;
        jwtRequestFilter.jwtTokenUtil = jwtTokenUtil;
        SecurityContextHolder.setContext(securityContext);
    }



    @Test
    public void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        String token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenUtil.getUsernameFromToken(token)).thenThrow(new IllegalArgumentException());

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(securityContext, never()).setAuthentication(any());
    }

    @Test
    public void testDoFilterInternal_ExpiredToken() throws ServletException, IOException {
        String token = "expired-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtTokenUtil.getUsernameFromToken(token)).thenThrow(new ExpiredJwtException(null, null, "JWT Token has expired"));

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(securityContext, never()).setAuthentication(any());
    }

    @Test
    public void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(securityContext, never()).setAuthentication(any());
    }
}
