package com.manifest.Manifest.service;

import com.manifest.Manifest.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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


}

