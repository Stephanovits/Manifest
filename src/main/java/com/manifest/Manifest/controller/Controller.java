package com.manifest.Manifest.controller;

import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.service.PatientTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private PatientTransportService patientTransportService;

    @GetMapping(value = "/")
    public String helloWorld(Model model) {
        model.addAttribute("listPatientTransport", patientTransportService.getAllPatientTransports());
        return "index";
    }

    //Displays patientTransportFrom
    @GetMapping(value = "/createPatientTransport")
    public String createPatientTransport(Model model) {
        PatientTransport patientTransport = new PatientTransport();
        model.addAttribute("patientTransport", patientTransport);
        return "patientTransportForm";
    }

    //Displays patientTransportUpdateFrom
    @GetMapping(value = "/updatePatientTransportById/{id}")
    public String updatePatientTransport(@PathVariable("id") Long jobId, Model model) {
        model.addAttribute("patientTransport", patientTransportService.getPatientTransportById(jobId));
        return "patientTransportUpdateForm";
    }

    //@RequestBody converts whatever JSON object I pass this method to a PatientTransport
    @PostMapping(value = "/savePatientTransport")
    public String savePatientTransport(@ModelAttribute("patientTransport") PatientTransport patientTransport) {
        patientTransportService.savePatientTransport(patientTransport);
        return "redirect:/";

    }

    @GetMapping(value = "/getPatientTransportById/{id}")
    public PatientTransport getPatientTransportById(@PathVariable("id") Long jobId){
        return patientTransportService.getPatientTransportById(jobId);
    }

    @GetMapping(value = "/getAllPatientTransports")
    public List<PatientTransport> getAllPatientTransports() {
        return patientTransportService.getAllPatientTransports();
    }

    @GetMapping(value = "/getPatientTransportsByWard/{ward}")
    public List<PatientTransport> getPatientTransportsByWard(@PathVariable("ward") String ward) {
        return patientTransportService.getPatientTransportByWard(ward);
    }


    @DeleteMapping("/deletePatientTransportById/{id}")
    public String deletePatientTransportById(@PathVariable("id") Long jobId){
        patientTransportService.deletePatientTransportById(jobId);
        return String.format("Patient Transport Job with patientId %s successfully deleted.", jobId);
    }

    @DeleteMapping(value = "/deleteAllPatientTransports")
    public String deleteAllPatientTransports(){
        patientTransportService.deleteAllPatientTransports();
        return "All Patient Transport Jobs successfully deleted.";
    }

}
