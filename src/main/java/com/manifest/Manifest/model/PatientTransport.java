package com.manifest.Manifest.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PatientTransport {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long jobId;
    private String patientName;
    private String patientWard;
    private String patientRoom;
    private String examination;
    private String status = "Waiting";
    private String type;

    public PatientTransport(Long jobId, String patientName, String patientWard, String patientRoom, String examination, String type) {
        this.jobId = jobId;
        this.patientName = patientName;
        this.patientWard = patientWard;
        this.patientRoom = patientRoom;
        this.examination = examination;
        this.type = type;
    }

    public PatientTransport() {
    }

    @Override
    public String toString() {
        return "PatientTransport{" +
                "jobId=" + jobId +
                ", patientName='" + patientName + '\'' +
                ", patientWard='" + patientWard + '\'' +
                ", patientRoom='" + patientRoom + '\'' +
                ", examination='" + examination + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientWard() {
        return patientWard;
    }

    public void setPatientWard(String patientWard) {
        this.patientWard = patientWard;
    }

    public String getPatientRoom() {
        return patientRoom;
    }

    public void setPatientRoom(String patientRoom) {
        this.patientRoom = patientRoom;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
