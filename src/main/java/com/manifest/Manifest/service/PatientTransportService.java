package com.manifest.Manifest.service;

import com.manifest.Manifest.dto.SelectionDto;
import com.manifest.Manifest.model.PatientTransport;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientTransportService {

    public PatientTransport savePatientTransport(PatientTransport patientTransport);

    PatientTransport getPatientTransportById(Long jobId);

    public List<PatientTransport> getAllPatientTransports();

    public void deleteAllPatientTransports();

    public void deletePatientTransportById(Long jobId);

    public PatientTransport updatePatientTransportById(Long jobId, PatientTransport patientTransport);

    public List<PatientTransport> getPatientTransportByWard(String ward);

    public PatientTransport movePatientTransportPhase(PatientTransport patientTransport);

    public PatientTransport revokePatientTransportPhase(PatientTransport patientTransport);

    public List<PatientTransport> getPatientTransportByCustom(SelectionDto selectionDto);
}
