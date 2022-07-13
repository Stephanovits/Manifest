package com.manifest.Manifest.controller;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.service.PatientTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private PatientTransportService patientTransportService;

    @GetMapping(value = "/")
    public String helloWorld() {
        return "Welcome to Manifest";
    }

    //@RequestBody converts whatever JSON object I pass this method to a PatientTransport
    @PostMapping(value = "/savePatientTransport")
    public PatientTransport savePatientTransport(@RequestBody PatientTransport patientTransport) {
        return patientTransportService.savePatientTransport( patientTransport);
    }

    @GetMapping(value = "/getAllPatientTransports")
    public List<PatientTransport> getAllPatientTransports() {
        return patientTransportService.getAllPatientTransports();
    }

    @DeleteMapping("/deletePatientTransportById/{id}")
    public String deletePatientTransportById(@PathVariable("id") Long jobId){
        patientTransportService.deletePatientTransportById(jobId);
        return "Patient Transport Job successfully deleted.";
    }


    @DeleteMapping(value = "/deleteAllPatientTransports")
    public String deleteAllPatientTransports(){
        patientTransportService.deleteAllPatientTransports();
        return "All Patient Transport Jobs successfully deleted.";
    }


}
