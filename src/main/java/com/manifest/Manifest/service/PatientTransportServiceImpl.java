package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.repository.PatientTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PatientTransportServiceImpl implements PatientTransportService{

    @Autowired
    private PatientTransportRepository patientTransportRepository;

    @Override
    public PatientTransport savePatientTransport(PatientTransport patientTransport) {
        return patientTransportRepository.save(patientTransport);
    }

    @Override
    public PatientTransport getPatientTransportById(Long jobId) {
        return patientTransportRepository.findById(jobId).get();
    }

    @Override
    public List<PatientTransport> getAllPatientTransports() {
        return patientTransportRepository.findAll();
    }

    @Override
    public void deleteAllPatientTransports() {
        patientTransportRepository.deleteAll();
    }

    @Override
    public void deletePatientTransportById(Long jobId) {
        patientTransportRepository.deleteById(jobId);
    }

    @Override
    public PatientTransport updatePatientTransportById(Long jobId, PatientTransport patientTransport) {
        PatientTransport ptDB = getPatientTransportById(jobId);

        //performing blank check and null checks
        if(Objects.nonNull(patientTransport.getPatientName()) && !"".equalsIgnoreCase(patientTransport.getPatientName())) {
            ptDB.setPatientName(patientTransport.getPatientName());
        }
        if(Objects.nonNull(patientTransport.getPatientWard()) && !"".equalsIgnoreCase(patientTransport.getPatientWard())) {
            ptDB.setPatientWard(patientTransport.getPatientWard());
        }
        if(Objects.nonNull(patientTransport.getPatientRoom()) && !"".equalsIgnoreCase(patientTransport.getPatientRoom())) {
            ptDB.setPatientRoom(patientTransport.getPatientRoom());
        }
        if(Objects.nonNull(patientTransport.getExamination()) && !"".equalsIgnoreCase(patientTransport.getExamination())) {
            ptDB.setExamination(patientTransport.getExamination());
        }
        if(Objects.nonNull(patientTransport.getStatus()) && !"".equalsIgnoreCase(patientTransport.getStatus())) {
            ptDB.setStatus(patientTransport.getStatus());
        }
        if(Objects.nonNull(patientTransport.getType()) && !"".equalsIgnoreCase(patientTransport.getType())) {
            ptDB.setType(patientTransport.getType());
        }

        return patientTransportRepository.save(ptDB);
    }

    @Override
    public List<PatientTransport> getPatientTransportByWard(String ward) {
        return patientTransportRepository.getPatientTransportByWard(ward);
    }


}
