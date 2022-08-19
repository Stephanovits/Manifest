package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Ward;
import com.manifest.Manifest.repository.WardRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WardServiceTest {

    @Autowired
    WardService wardService;

    @MockBean
    WardRepository wardRepository;

    @Test
    void saveWardTest(){
        //GIVEN
        Ward testWard = new Ward();
        testWard.setWardName("W1");
        //WHEN
        Mockito.when(wardRepository.save(testWard)).thenReturn(testWard);
        //THEN
        Ward expected = testWard;
        Ward actual = wardService.saveWard(testWard);
        assertEquals(expected, actual);
    }

    @Test
    void getWardById(){
        //GIVEN
        Ward testWard = new Ward();
        testWard.setWardName("W1");
        //WHEN
        Mockito.when(wardRepository.findById(1L)).thenReturn(Optional.of(testWard));
        //THEN
        Ward expected = testWard;
        Ward actual = wardService.getWardById(1L);
        assertEquals(expected, actual);
    }



}
