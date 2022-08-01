package com.manifest.Manifest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Examination {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long examinationId;
    private String examinationName;

    public Examination() {
    }

    @Override
    public String toString() {
        return "Examination{" +
                "examinationId=" + examinationId +
                ", examinationName='" + examinationName + '\'' +
                '}';
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
