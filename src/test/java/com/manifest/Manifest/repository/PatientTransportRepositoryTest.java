package com.manifest.Manifest.repository;

import com.manifest.Manifest.model.PatientTransport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PatientTransportRepositoryTest {

    @Autowired
    PatientTransportRepository patientTransportRepository;

    @AfterEach
    void tearDown(){
        patientTransportRepository.deleteAll();
    }

    @Test
    void getPatientTransportByWardTEST_POSITIVE() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");
        patientTransportRepository.save(pt1);
        List<PatientTransport> expected = new ArrayList<PatientTransport>();
        expected.add(pt1);
        //when
        List<PatientTransport> actual = patientTransportRepository.getPatientTransportByWard("W1");
        //then
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void getPatientTransportByWardTEST_NEGATIVE() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");
        patientTransportRepository.save(pt1);
        //when
        List<PatientTransport> actual = patientTransportRepository.getPatientTransportByWard("W2");
        //then
        assertEquals(0, actual.size());
    }
}