package com.manifest.Manifest.service;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.model.Ward;

import java.util.List;

public interface WardService {

    public Ward saveWard(Ward ward);

    public Ward getWardById(Long wardId);

    public List<Ward> getAllWards();

    public void deleteWardById(Long wardId);

}
