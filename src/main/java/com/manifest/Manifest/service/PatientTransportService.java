package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;

import java.util.List;

public interface PatientTransportService {

    public PatientTransport savePatientTransport(PatientTransport patientTransport);

    PatientTransport getPatientTransportById(Long jobId);

    public List<PatientTransport> getAllPatientTransports();

    public void deleteAllPatientTransports();

    public void deletePatientTransportById(Long jobId);

    public PatientTransport updatePatientTransportById(Long jobId, PatientTransport patientTransport);

}
