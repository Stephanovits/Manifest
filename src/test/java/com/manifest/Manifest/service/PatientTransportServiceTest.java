package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.repository.PatientTransportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientTransportServiceTest {

    @Autowired
    private PatientTransportService patientTransportService;

    @MockBean
    private PatientTransportRepository patientTransportRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void savePatientTransportTEST() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");
        //when
        Mockito.when(patientTransportRepository.save(pt1)).thenReturn(pt1);
        //then
        assertEquals(pt1, patientTransportService.savePatientTransport(pt1));
    }

    @Test
    void getPatientTransportByIdTEST() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");
        Long l = Integer.toUnsignedLong(1);
        //when
        Mockito.when(patientTransportRepository.findById(l)).thenReturn(Optional.of(pt1));
        //then
        assertEquals(pt1, patientTransportService.getPatientTransportById(l));
    }

    @Test
    void getAllPatientTransportsTEST() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt1.setPatientName("Maier");
        pt1.setPatientWard("W2");
        pt1.setPatientRoom("999");
        pt1.setExamination("MR");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        List<PatientTransport> expected = new ArrayList<PatientTransport>();
        expected.add(pt1);
        expected.add(pt2);
        //when
        Mockito.when(patientTransportRepository.findAll()).thenReturn(expected);
        //then
        assertEquals(expected.size(), patientTransportService.getAllPatientTransports().size());
    }

    @Test
    void deleteAllPatientTransportsTEST() {
        patientTransportService.deleteAllPatientTransports();
        Mockito.verify(patientTransportRepository).deleteAll();
    }

    @Test
    void deletePatientTransportByIdTEST() {
        Long l = Integer.toUnsignedLong(1);
        patientTransportService.deletePatientTransportById(l);
        Mockito.verify(patientTransportRepository, Mockito.times(1)).deleteById(l);
    }

    @Test
    void updatePatientTransportByIdTEST() {
        //given
        Long l = Integer.toUnsignedLong(1);

        PatientTransport old = new PatientTransport();
        old.setJobId(l);
        old.setPatientName("Huber");
        old.setPatientWard("W1");
        old.setPatientRoom("999");
        old.setExamination("CD");
        old.setStatus("Waiting");
        old.setType("Routine");

        PatientTransport updated = new PatientTransport();
        updated.setJobId(l);
        updated.setPatientName("Huber");
        updated.setPatientWard("W1");
        updated.setPatientRoom("123");
        updated.setExamination("CD");
        updated.setStatus("Waiting");
        updated.setType("Routine");

        //when
        Mockito.when(patientTransportRepository.findById(l)).thenReturn(Optional.of(old));
        Mockito.when(patientTransportRepository.save(updated)).thenReturn(updated);

        PatientTransport actual = patientTransportService.updatePatientTransportById(l, updated);

        //then
        //assertEquals(old, actual);
    }

    @Test
    void getPatientTransportByWardTEST() {
        //given
        PatientTransport pt1 = new PatientTransport();
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt1.setPatientName("Maier");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("999");
        pt1.setExamination("MR");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        List<PatientTransport> expected = new ArrayList<PatientTransport>();
        expected.add(pt1);
        expected.add(pt2);

        //when
        Mockito.when(patientTransportRepository.getPatientTransportByWard("W1")).thenReturn(expected);
        //then
        assertEquals(expected, patientTransportService.getPatientTransportByWard("W1"));
    }

}