package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.repository.PatientTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //TODO
    @Override
    public PatientTransport updatePatientTransportById(Long jobId, PatientTransport patientTransport) {
        return new PatientTransport();
    }

}
