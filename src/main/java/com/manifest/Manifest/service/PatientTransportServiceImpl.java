package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.repository.PatientTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public PatientTransport movePatientTransportPhase(PatientTransport patientTransport) {
        PatientTransport output = patientTransport;
        String currentStatus = patientTransport.getStatus();

        switch(currentStatus) {
            case "Waiting" :
                output.setStatus("Requested for Examination");
                break;
            case "Requested for Examination" :
                output.setStatus("Transit to Examination");
                break;
            case "Transit to Examination" :
                output.setStatus("Patient at Examination");
                break;
            case "Patient at Examination" :
                output.setStatus("Examination Completed");
                break;
            case "Examination Completed" :
                output.setStatus("Transit to Ward");
                break;
            case "Transit to Ward" :
                output.setStatus("Completed");
                break;
        }
        return output;
    }

    @Override
    public PatientTransport revokePatientTransportPhase(PatientTransport patientTransport) {
        PatientTransport output = patientTransport;
        String currentStatus = patientTransport.getStatus();

        switch(currentStatus) {
            case "Completed" :
                output.setStatus("Transit to Ward");
                break;
            case "Transit to Ward" :
                output.setStatus("Examination Completed");
                break;
            case "Examination Completed" :
                output.setStatus("Patient at Examination");
                break;
            case "Patient at Examination" :
                output.setStatus("Transit to Examination");
                break;
            case "Transit to Examination" :
                output.setStatus("Requested for Examination");
                break;
            case "Requested for Examination" :
                output.setStatus("Waiting");
                break;
        }
        return output;
    }


}
