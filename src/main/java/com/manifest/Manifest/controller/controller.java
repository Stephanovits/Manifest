package com.manifest.Manifest.controller;

import com.manifest.Manifest.model.PatientTransport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @GetMapping(value = "/")
    public String helloWorld() {
        return "Welcome to Manifest";
    }


    @PostMapping(value = "/savePatientTransport")
    public PatientTransport savePatientTransport(@RequestBody PatientTransport patientTransport) {

    }

}
