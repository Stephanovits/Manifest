package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.repository.ExaminationRepository;
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
public class ExaminationServiceTest {

    @MockBean
    ExaminationRepository examinationRepository;

    @Autowired
    ExaminationService examinationService;

    @Test
    void saveExaminationTest() {
        //given
        Examination testExam = new Examination();
        testExam.setExaminationName("CD");
        //when
        Mockito.when(examinationRepository.save(testExam)).thenReturn(testExam);
        //then
        Examination expected = testExam;
        Examination accepted = examinationService.saveExamination(testExam);
        assertEquals(expected, accepted);
    }

    @Test
    void getExaminationByIdTest(){
        //given
        Examination testExam = new Examination();
        testExam.setExaminationName("CD");
        //when
        Mockito.when(examinationRepository.findById(1L)).thenReturn(Optional.of(testExam));
        //then
        Examination expected = testExam;
        Examination accepted = examinationService.getExaminationById(1L);
        assertEquals(expected, accepted);
    }

    @Test
    void getAllExaminationsTest(){
        //given
        Examination testExam = new Examination();
        testExam.setExaminationName("CD");

        Examination testExam2 = new Examination();
        testExam2.setExaminationName("MR");

        List<Examination> expected = new ArrayList<>();
        expected.add(testExam2);
        expected.add(testExam);
        //when
        Mockito.when(examinationRepository.findAll()).thenReturn(expected);
        //then
        List<Examination> accepted = examinationService.getAllExaminations();
        // - also testing for correct sorting
        assertEquals(expected.get(0).getExaminationName(), "CD");
    }

    @Test
    void deleteExaminationByIdTest(){
        //when
        examinationService.deleteExaminationById(1L);
        Mockito.verify(examinationRepository).deleteById(1L);
    }
}
