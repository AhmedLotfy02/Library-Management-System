package com.LibraryManagementSystem.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "mysecret");
    }

    @Test
    public void testGenerateToken() {
        userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testGetUsernameFromToken() {
        userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testGetExpirationDateFromToken() {
        userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails);
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    public void testValidateToken() {
        userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails);
        boolean isValid = jwtTokenUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    private String createTokenWithExpiration(Date expirationDate) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, "mysecret")
                .compact();
    }
}
