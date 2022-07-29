package com.manifest.Manifest.service;

import com.manifest.Manifest.dto.SelectionAttribute;
import com.manifest.Manifest.dto.SelectionDto;
import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.repository.PatientTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PatientTransportServiceImpl implements PatientTransportService{

    @Autowired
    private PatientTransportRepository patientTransportRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public List<PatientTransport> getPatientTransportByCustom(SelectionDto selectionDto) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransport> criteriaQuery = criteriaBuilder.createQuery(PatientTransport.class);
        Root<PatientTransport> root = criteriaQuery.from(PatientTransport.class);

        //defining criteria for ward-selection
        List<Predicate> wardCriterias = new ArrayList<>();
        for(SelectionAttribute ward: selectionDto.getWardList()) {
            if (ward.getSelected()){
                wardCriterias.add(criteriaBuilder.equal(root.get("patientWard"), ward.getAttributeName()));
            }
        }
        Predicate wardPredicate = criteriaBuilder.or(wardCriterias.toArray(new Predicate[wardCriterias.size()]));

        //defining criteria for examination-selection
        List<Predicate> examinationCriterias = new ArrayList<>();
        for(SelectionAttribute exam: selectionDto.getExaminationList()) {
            if (exam.getSelected()){
                examinationCriterias.add(criteriaBuilder.equal(root.get("examination"), exam.getAttributeName()));
            }
        }
        Predicate examinationPredicate = criteriaBuilder.or(examinationCriterias.toArray(new Predicate[examinationCriterias.size()]));

        //defining criteria for including or excluding completed jobs
        Predicate statusCriteria;
        if(selectionDto.getIncCompletedJobs()){
            statusCriteria = criteriaBuilder.like(root.get("status"), "%");
        } else {
            statusCriteria = criteriaBuilder.notEqual(root.get("status"), "completed");
        }


        Predicate finalPredicate = criteriaBuilder.and(wardPredicate, examinationPredicate, statusCriteria);
        criteriaQuery.where(finalPredicate);

        List<PatientTransport> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result;

    }

}