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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    User testUser;
    User testUser2;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("Test User 1");
        testUser.setPassword("Password123");
        testUser.setRole("ADMIN");

        testUser2 = new User();
        testUser2.setUsername("Test User 2");
        testUser2.setPassword("PasswordABC");
        testUser2.setRole("WORKER");
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
    void getAllUsersTest(){
        //given
        List<User> expected = new ArrayList<>();
        expected.add(testUser);
        expected.add(testUser2);
        Mockito.when(userRepository.findAll()).thenReturn(expected);
        //when
        List<User> actual = new ArrayList<>();
        actual.add(testUser);
        actual.add(testUser2);
        //then
        assertEquals(expected.size(), actual.size());
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

    @Test
    void getUserByIdTest(){
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        User expected = testUser;
        //when
        User actual = customUserDetailsService.getUserById(1L);
        //then
        assertEquals(actual, expected);
    }


    @Test
    void l(){
        //given

        //when

        //then

    }

}

