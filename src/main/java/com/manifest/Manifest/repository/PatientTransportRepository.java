package com.manifest.Manifest.repository;

import com.manifest.Manifest.model.PatientTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientTransportRepository extends JpaRepository<PatientTransport, Long> {

    @Query(value = "SELECT * FROM PATIENT_TRANSPORT WHERE PATIENT_WARD = ?1", nativeQuery = true)
    public List<PatientTransport> getPatientTransportByWard(String ward);

}
