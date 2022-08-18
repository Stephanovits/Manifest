package com.manifest.Manifest.service;

import com.manifest.Manifest.model.User;
import com.manifest.Manifest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("Test User 1");
        testUser.setPassword("Password123");
        testUser.setRole("ADMIN");
    }

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    UserRepository userRepository;

    @Test
    void loadUserByUsernameTest(){
        //given
        Mockito.when(userRepository.findByUsername("Test User 1")).thenReturn(testUser);
        //when
        UserDetails actual = customUserDetailsService.loadUserByUsername("Test User 1");
        UserDetails expected = new CustomUserDetails(testUser);
        //then
        assertEquals(actual.getUsername(), expected.getUsername());
    }

    @Test
    void l(){
        //given

        //when

        //then

    }

    @Test
    void saveUserTest() {
        //given
        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
        //when
        User expected = testUser;
        User actual = customUserDetailsService.saveUser(testUser);
        //then
        assertEquals(expected.getUsername(), actual.getUsername());
    }


}

