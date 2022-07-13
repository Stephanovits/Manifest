package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;

import java.util.List;

public interface PatientTransportService {

    public PatientTransport savePatientTransport(PatientTransport patientTransport);

    public List<PatientTransport> getAllPatientTransports();

    public void deleteAllPatientTransports();

    public void deletePatientTransportById(Long jobId);
}
