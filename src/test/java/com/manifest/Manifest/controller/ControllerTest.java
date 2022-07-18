package com.manifest.Manifest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.service.PatientTransportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientTransportService patientTransportService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void helloWorldTEST() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult result = mockMvc.perform(request).andReturn();
        //have to use getResponse() and NOT getRequest()!!!
        assertEquals("Welcome to Manifest", result.getResponse().getContentAsString());
    }


    @Test
    void savePatientTransportTEST() throws Exception {
        PatientTransport patientTransport = new PatientTransport();
        patientTransport.setPatientName("Huber");
        patientTransport.setPatientWard("W1");
        patientTransport.setPatientRoom("123");
        patientTransport.setExamination("CD");
        patientTransport.setStatus("Waiting");
        patientTransport.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt2.setJobId(Integer.toUnsignedLong(1));
        pt2.setPatientName("Huber");
        pt2.setPatientWard("W1");
        pt2.setPatientRoom("123");
        pt2.setExamination("CD");
        pt2.setStatus("Waiting");
        pt2.setType("Routine");

        Mockito.when(patientTransportService.savePatientTransport(Mockito.any(PatientTransport.class))).thenReturn(pt2);

        String exampleJson = "{\"patientName\":\"Huber\",\"patientWard\":\"W1\",\"patientRoom\":\"123\",\"examination\":\"CD\",\"status\":\"Waiting\",\"type\":\"Routine\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/savePatientTransport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleJson);

        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPatientTransportByIdTEST() throws Exception {

        PatientTransport pt2 = new PatientTransport();
        pt2.setJobId(Integer.toUnsignedLong(1));
        pt2.setPatientName("Huber");
        pt2.setPatientWard("W1");
        pt2.setPatientRoom("123");
        pt2.setExamination("CD");
        pt2.setStatus("Waiting");
        pt2.setType("Routine");

        Mockito.when(patientTransportService.getPatientTransportById(1L)).thenReturn(pt2);

        mockMvc.perform(MockMvcRequestBuilders.get("/getPatientTransportById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientName").value(pt2.getPatientName()));
    }

    @Test
    void getAllPatientTransportsTEST() {
    }

    @Test
    void getPatientTransportsByWardTEST() {
    }

    @Test
    void deletePatientTransportByIdTEST() {
    }

    @Test
    void deleteAllPatientTransportsTEST() {

    }

    @Test
    void updatePatientTransportByIdTEST() {
    }
}
