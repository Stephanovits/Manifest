package com.manifest.Manifest.service;

import com.manifest.Manifest.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomUserDetailsTest {

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("Test User 1");
        testUser.setPassword("Password123");
        testUser.setRole("ADMIN");
    }

    @Test
    void getAuthoritiesTest() {
        //given
        CustomUserDetails cud = new CustomUserDetails(testUser);
        Collection<SimpleGrantedAuthority> expected = new ArrayList<>();
        expected.add(new SimpleGrantedAuthority(testUser.getRole()));
        //when
        Collection<? extends GrantedAuthority> actual = cud.getAuthorities();
        //then
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void getPasswordTest(){
        //given
        CustomUserDetails cud = new CustomUserDetails(testUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //when
        String actual = cud.getPassword();
        String expected = "Password123";
        //then
        assertTrue(encoder.matches(expected, actual));
    }

    @Test
    void getUsernameTest(){
        //given
        CustomUserDetails cud = new CustomUserDetails(testUser);
        //when
        String actual = cud.getUsername();
        String expected = "Test User 1";
        //then
        assertEquals(expected, actual);
    }

}

