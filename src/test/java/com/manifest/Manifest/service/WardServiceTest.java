package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Ward;
import com.manifest.Manifest.repository.WardRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
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
    void getWardByIdTest(){
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

    @Test
    void getAllWardsTest(){
        //GIVEN
        Ward testWard = new Ward();
        testWard.setWardName("W1");
        Ward testWard2 = new Ward();
        testWard2.setWardName("W2");

        List<Ward> expected = new ArrayList<>();
        expected.add(testWard2);
        expected.add(testWard);

        //WHEN
        Mockito.when(wardRepository.findAll()).thenReturn(expected);

        //THEN
        List<Ward> actual = wardService.getAllWards();
        assertEquals(actual.get(0).getWardName(), "W1"); // checking for correct sorted output
        assertEquals(actual.get(1).getWardName(), "W2"); // checking for correct sorted output
    }

    @Test
    void deleteWardByIdTest(){
        wardService.deleteWardById(1L);
        Mockito.verify(wardRepository).deleteById(1L);
    }

}
