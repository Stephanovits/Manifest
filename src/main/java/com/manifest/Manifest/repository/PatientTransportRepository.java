package com.manifest.Manifest.repository;

import com.manifest.Manifest.model.PatientTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientTransportRepository extends JpaRepository<PatientTransport, Long> {
}
