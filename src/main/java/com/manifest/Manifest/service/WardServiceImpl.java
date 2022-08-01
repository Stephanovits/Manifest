package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.model.Ward;
import com.manifest.Manifest.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardServiceImpl implements WardService{

    @Autowired
    private WardRepository wardRepository;

    @Override
    public Ward saveWard(Ward ward) {
        return wardRepository.save(ward);
    }

    @Override
    public Ward getWardById(Long wardId) {
        return wardRepository.findById(wardId).get();
    }

    @Override
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    @Override
    public void deleteWardById(Long wardId) {
        wardRepository.deleteById(wardId);
    }
}
