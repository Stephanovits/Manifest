package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.repository.ExaminationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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



}
