package com.manifest.Manifest.controller;

import com.manifest.Manifest.dto.SelectionAttribute;
import com.manifest.Manifest.dto.SelectionDto;
import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.model.User;
import com.manifest.Manifest.model.Ward;
import com.manifest.Manifest.repository.ExaminationRepository;
import com.manifest.Manifest.repository.UserRepository;
import com.manifest.Manifest.service.ExaminationService;
import com.manifest.Manifest.service.PatientTransportService;
import com.manifest.Manifest.service.WardService;
import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private PatientTransportService patientTransportService;

    @Autowired
    private WardService wardService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/")
    public String helloWorld(Model model, @RequestParam Map<String,String> allParams, SelectionDto selectionDto) {

        List<Ward> wardData = wardService.getAllWards();

        List<Examination> examData = examinationService.getAllExaminations();

        if(selectionDto.getWardList() == null){
            System.out.println(">>> RUNNING INITIAL DTO-SETUP");
            List<SelectionAttribute> l1 = new ArrayList<>();

            for(Ward w: wardData) {
                SelectionAttribute x = new SelectionAttribute();
                x.setAttributeName(w.getWardName());
                x.setSelected(true);
                l1.add(x);
            }
            selectionDto.setWardList(l1);

            List<SelectionAttribute> l2 = new ArrayList<>();

            for(Examination e: examData) {
                SelectionAttribute x = new SelectionAttribute();
                x.setAttributeName(e.getExaminationName());
                x.setSelected(true);
                l2.add(x);
            }
            selectionDto.setExaminationList(l2);

            selectionDto.setIncCompletedJobs(false);
            selectionDto.setSort(SelectionDto.Sort.WARD);
        }


        System.out.println( ">>> CURRENT DTO: " + selectionDto);
        model.addAttribute("selectionDto", selectionDto);
        model.addAttribute("listPatientTransport", patientTransportService.getPatientTransportByCustom(selectionDto));

        return "index";
    }

    //Displays patientTransportFrom
    @GetMapping(value = "/createPatientTransport")
    public String createPatientTransport(Model model) {
        PatientTransport patientTransport = new PatientTransport();
        model.addAttribute("patientTransport", patientTransport);
        model.addAttribute("wards", wardService.getAllWards());
        model.addAttribute("examinations", examinationService.getAllExaminations());
        return "patientTransportForm";
    }

    //Displays patientTransportUpdateFrom
    @GetMapping(value = "/updatePatientTransportById/{id}")
    public String updatePatientTransport(@PathVariable("id") Long jobId, Model model) {
        model.addAttribute("patientTransport", patientTransportService.getPatientTransportById(jobId));
        model.addAttribute("wards", wardService.getAllWards());
        model.addAttribute("examinations", examinationService.getAllExaminations());
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


    @GetMapping("/deletePatientTransportById/{id}")
    public String deletePatientTransportById(@PathVariable("id") Long jobId){
        patientTransportService.deletePatientTransportById(jobId);
        return "redirect:/";
    }

    @DeleteMapping(value = "/deleteAllPatientTransports")
    public String deleteAllPatientTransports(){
        patientTransportService.deleteAllPatientTransports();
        return "All Patient Transport Jobs successfully deleted.";
    }

    //Moves the status of a PatientTransport to next pahse
    @GetMapping(value = "/movePatientTransportPhase/{id}")
    public String movePatientTransportPhase(@PathVariable("id") Long jobId) {
        PatientTransport patientTransportToMove = patientTransportService.getPatientTransportById(jobId);
        System.out.println(patientTransportToMove.getJobId());

        patientTransportService.savePatientTransport(patientTransportService.movePatientTransportPhase(patientTransportToMove));
        return "redirect:/";
    }

    @GetMapping(value = "/revokePatientTransportPhase/{id}")
    public String revokePatientTransportPhase(@PathVariable("id") Long jobId) {
        PatientTransport patientTransportToRevoke = patientTransportService.getPatientTransportById(jobId);
        patientTransportService.savePatientTransport(patientTransportService.revokePatientTransportPhase(patientTransportToRevoke));
        return "redirect:/";
    }


    //ADMIN RELATED CONTROLLER
    @GetMapping(value = "/admin")
    public String admin(Model model) {
        model.addAttribute("wards", wardService.getAllWards());
        model.addAttribute("examinations", examinationService.getAllExaminations());
        model.addAttribute("ward", new Ward());
        model.addAttribute("examination", new Examination());
        return "admin";
    }

    @PostMapping(value = "/saveWard")
    public String saveWard(@ModelAttribute("ward") Ward ward) {
        wardService.saveWard(ward);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteWardById/{id}")
    public String deleteWardById(@PathVariable("id") Long wardId) {
        wardService.deleteWardById(wardId);
        return "redirect:/admin";
    }

    @PostMapping(value = "/saveExamination")
    public String saveExam(@ModelAttribute("examination") Examination examination) {
        examinationService.saveExamination(examination);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteExaminationById/{id}")
    public String deleteExaminationById(@PathVariable("id") Long wardId) {
        examinationService.deleteExaminationById(wardId);
        return "redirect:/admin";
    }


    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        return "redirect:/admin";
    }
}
