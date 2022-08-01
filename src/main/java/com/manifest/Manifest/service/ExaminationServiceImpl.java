package com.manifest.Manifest.service;

import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.model.Ward;
import com.manifest.Manifest.repository.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService{

    @Autowired
    private ExaminationRepository examinationRepository;

    @Override
    public Examination saveExamination(Examination examination) {
        return examinationRepository.save(examination);
    }

    @Override
    public Examination getExaminationById(Long examinationId) {
        return examinationRepository.findById(examinationId).get();
    }

    @Override
    public List<Examination> getAllExaminations() {
        List<Examination> output = examinationRepository.findAll();
        output.sort(Comparator.comparing(Examination::getExaminationName));
        return output;
    }

    @Override
    public void deleteExaminationById(Long examinationId) {
        examinationRepository.deleteById(examinationId);
    }
}
