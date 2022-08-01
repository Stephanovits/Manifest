package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.model.Ward;

import java.util.List;

public interface ExaminationService {

    public Examination saveExamination(Examination examination);

    public Examination getExaminationById(Long examinationId);

    public List<Examination> getAllExaminations();

    public void deleteExaminationById(Long examinationId);

}
