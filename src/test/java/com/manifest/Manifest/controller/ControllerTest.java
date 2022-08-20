package com.manifest.Manifest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.manifest.Manifest.configuration.SecurityConfiguration;
import com.manifest.Manifest.dto.SelectionDto;
import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.service.CustomUserDetailsService;
import com.manifest.Manifest.service.ExaminationService;
import com.manifest.Manifest.service.PatientTransportService;
import com.manifest.Manifest.service.WardService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(Controller.class)
@Import(Controller.class)
//@Import(ControllerTest.Config.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientTransportService patientTransportService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private WardService wardService;

    @MockBean
    private ExaminationService examinationService;

    @Configuration
    @EnableWebSecurity
    static class Config extends WebSecurityConfigurerAdapter {
        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password("123").authorities("USER");
            auth.inMemoryAuthentication().withUser("admin").password("123").authorities("ADMIN");
        }
    }

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
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void indexTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");

        PatientTransport pt1 = new PatientTransport(1L, "Patient Name 1", "W1", "444", "CD", "ROUTINE");
        PatientTransport pt2 = new PatientTransport(2L, "Patient Name 2", "W2", "666", "MR", "EMERGENCY");
        List<PatientTransport> l1 = new ArrayList<>();
        l1.add(pt1);
        l1.add(pt2);

        Mockito.when(patientTransportService.getPatientTransportByCustom(Mockito.any(SelectionDto.class))).thenReturn(l1);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("selectionDto"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listPatientTransport"))
                .andExpect(MockMvcResultMatchers.model().attribute("listPatientTransport", Matchers.equalTo(l1)));
    }



    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
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
                .content(exampleJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(302));

    }

    @Test
    @WithMockUser(username="Admin", password="123", roles={"ADMIN"})
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
    void getAllPatientTransportsTEST() throws Exception {
        List<PatientTransport> expected = new ArrayList<PatientTransport>();

        PatientTransport pt1 = new PatientTransport();
        pt1.setJobId(Integer.toUnsignedLong(1));
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt2.setJobId(Integer.toUnsignedLong(2));
        pt2.setPatientName("Weber");
        pt2.setPatientWard("W2");
        pt2.setPatientRoom("999");
        pt2.setExamination("MR");
        pt2.setStatus("Waiting");
        pt2.setType("Emergency");

        expected.add(pt1);
        expected.add(pt2);

        Mockito.when(patientTransportService.getAllPatientTransports()).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/getAllPatientTransports"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientName").value(pt1.getPatientName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].patientName").value(pt2.getPatientName()));

    }

    @Test
    void getPatientTransportsByWardTEST() throws Exception {
        List<PatientTransport> expected = new ArrayList<PatientTransport>();

        PatientTransport pt1 = new PatientTransport();
        pt1.setJobId(Integer.toUnsignedLong(1));
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("123");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt2.setJobId(Integer.toUnsignedLong(2));
        pt2.setPatientName("Weber");
        pt2.setPatientWard("W1");
        pt2.setPatientRoom("999");
        pt2.setExamination("MR");
        pt2.setStatus("Waiting");
        pt2.setType("Emergency");

        expected.add(pt1);
        expected.add(pt2);

        Mockito.when(patientTransportService.getPatientTransportByWard("W1")).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/getPatientTransportsByWard/W1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientName").value(pt1.getPatientName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].patientName").value(pt2.getPatientName()));
    }

    @Test
    @WithMockUser(username="Admin", password="123", authorities={"ADMIN"})
    void deletePatientTransportByIdTEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/deletePatientTransportById/1"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    void deleteAllPatientTransportsTEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAllPatientTransports"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePatientTransportByIdTEST() throws Exception {
        PatientTransport pt1 = new PatientTransport();
        pt1.setJobId(Integer.toUnsignedLong(1));
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("000");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        PatientTransport pt2 = new PatientTransport();
        pt2.setJobId(Integer.toUnsignedLong(1));
        pt2.setPatientName("Huber");
        pt2.setPatientWard("W1");
        pt2.setPatientRoom("999");
        pt2.setExamination("CD");
        pt2.setStatus("Waiting");
        pt2.setType("Routine");

        String exampleJson = "{\"patientName\":\"Huber\",\"patientWard\":\"W1\",\"patientRoom\":\"000\",\"examination\":\"CD\",\"status\":\"Waiting\",\"type\":\"Routine\"}";

        Mockito.when(patientTransportService.updatePatientTransportById(Mockito.any(Long.class), Mockito.any(PatientTransport.class))).thenReturn(pt2);

        mockMvc.perform(MockMvcRequestBuilders.put("/updatePatientTransportById/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientRoom").value(pt2.getPatientRoom()));
    }

    @Test
    void loginTEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}
