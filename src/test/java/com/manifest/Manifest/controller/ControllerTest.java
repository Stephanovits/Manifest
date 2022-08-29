package com.manifest.Manifest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.manifest.Manifest.configuration.SecurityConfiguration;
import com.manifest.Manifest.dto.SelectionDto;
import com.manifest.Manifest.model.Examination;
import com.manifest.Manifest.model.PatientTransport;
import com.manifest.Manifest.model.User;
import com.manifest.Manifest.model.Ward;
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
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void updatePatientTransportByIdTEST() throws Exception {
        PatientTransport pt1 = new PatientTransport();
        pt1.setJobId(1L);
        pt1.setPatientName("Huber");
        pt1.setPatientWard("W1");
        pt1.setPatientRoom("000");
        pt1.setExamination("CD");
        pt1.setStatus("Waiting");
        pt1.setType("Routine");

        Ward w1 = new Ward();
        w1.setWardName("W1");
        w1.setWardId(1L);
        Ward w2 = new Ward();
        w2.setWardName("W2");
        w2.setWardId(2L);

        List<Ward> wl = new ArrayList<>();
        wl.add(w1);
        wl.add(w2);

        Examination e1 =new Examination();
        e1.setExaminationName("CD");
        e1.setExaminationId(1L);
        Examination e2 = new Examination();
        e2.setExaminationName("MR");
        e2.setExaminationId(2L);

        List<Examination> el = new ArrayList<>();
        el.add(e1);
        el.add(e2);

        Mockito.when(patientTransportService.getPatientTransportById(Mockito.any(Long.class))).thenReturn(pt1);
        Mockito.when(wardService.getAllWards()).thenReturn(wl);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(el);

        mockMvc.perform(MockMvcRequestBuilders.get("/updatePatientTransportById/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patientTransportUpdateForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patientTransport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("wards"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("examinations"))
                .andExpect(MockMvcResultMatchers.model().attribute("patientTransport", Matchers.equalTo(pt1)))
                .andExpect(MockMvcResultMatchers.model().attribute("wards", Matchers.equalTo(wl)))
                .andExpect(MockMvcResultMatchers.model().attribute("examinations", Matchers.equalTo(el)));

    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void createPatientTransportTEST() throws Exception {
        PatientTransport pt1 = new PatientTransport();

        Ward w1 = new Ward();
        w1.setWardName("W1");
        w1.setWardId(1L);
        Ward w2 = new Ward();
        w2.setWardName("W2");
        w2.setWardId(2L);

        List<Ward> wl = new ArrayList<>();
        wl.add(w1);
        wl.add(w2);

        Examination e1 =new Examination();
        e1.setExaminationName("CD");
        e1.setExaminationId(1L);
        Examination e2 = new Examination();
        e2.setExaminationName("MR");
        e2.setExaminationId(2L);

        List<Examination> el = new ArrayList<>();
        el.add(e1);
        el.add(e2);

        Mockito.when(wardService.getAllWards()).thenReturn(wl);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(el);

        mockMvc.perform(MockMvcRequestBuilders.get("/createPatientTransport"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patientTransportForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patientTransport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("wards"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("examinations"))
                .andExpect(MockMvcResultMatchers.model().attribute("wards", Matchers.equalTo(wl)))
                .andExpect(MockMvcResultMatchers.model().attribute("examinations", Matchers.equalTo(el)));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void movePatientTransportPhaseTEST() throws Exception {
        PatientTransport pt1 = new PatientTransport();

        Mockito.when(patientTransportService.getPatientTransportById(Mockito.any(Long.class))).thenReturn(pt1);
        Mockito.when(patientTransportService.movePatientTransportPhase(Mockito.any(PatientTransport.class))).thenReturn(pt1);

        mockMvc.perform(MockMvcRequestBuilders.get("/movePatientTransportPhase/1"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(patientTransportService).getPatientTransportById(1L);
        Mockito.verify(patientTransportService).savePatientTransport(Mockito.any(PatientTransport.class));
    }


    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void revokePatientTransportPhaseTEST() throws Exception {
        PatientTransport pt1 = new PatientTransport();

        Mockito.when(patientTransportService.getPatientTransportById(Mockito.any(Long.class))).thenReturn(pt1);
        Mockito.when(patientTransportService.revokePatientTransportPhase(Mockito.any(PatientTransport.class))).thenReturn(pt1);

        mockMvc.perform(MockMvcRequestBuilders.get("/revokePatientTransportPhase/1"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(patientTransportService).getPatientTransportById(1L);
        Mockito.verify(patientTransportService).savePatientTransport(Mockito.any(PatientTransport.class));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void adminTEST() throws Exception {
        Ward w1 = new Ward();
        w1.setWardName("W1");
        w1.setWardId(1L);
        Ward w2 = new Ward();
        w2.setWardName("W2");
        w2.setWardId(2L);

        List<Ward> wl = new ArrayList<>();
        wl.add(w1);
        wl.add(w2);

        Examination e1 =new Examination();
        e1.setExaminationName("CD");
        e1.setExaminationId(1L);
        Examination e2 = new Examination();
        e2.setExaminationName("MR");
        e2.setExaminationId(2L);

        List<Examination> el = new ArrayList<>();
        el.add(e1);
        el.add(e2);

        User u1 = new User();
        u1.setUsername("Thomas");
        u1.setRole("WORKER");
        u1.setUserId(1L);
        u1.setPassword("123");
        User u2 = new User();
        u2.setUsername("Alex");
        u2.setRole("ADMIN");
        u2.setUserId(2L);
        u2.setPassword("123");

        List<User> ul = new ArrayList<>();
        ul.add(u1);
        ul.add(u2);

        Mockito.when(wardService.getAllWards()).thenReturn(wl);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(el);
        Mockito.when(customUserDetailsService.getAllUsers()).thenReturn(ul);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("wards"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("examinations"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ward"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("examination"))
                .andExpect(MockMvcResultMatchers.model().attribute("wards", Matchers.equalTo(wl)))
                .andExpect(MockMvcResultMatchers.model().attribute("examinations", Matchers.equalTo(el)))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.equalTo(ul)));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void saveWardTEST() throws Exception {
        Ward w1 = new Ward();
        w1.setWardName("W1");
        w1.setWardId(1L);

        RequestBuilder request = MockMvcRequestBuilders.post("/saveWard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"wardName\":\"W1\",\"wardId\":\"1\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(wardService).saveWard(Mockito.any(Ward.class));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void deleteWardByIdTEST() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteWardById/1"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(wardService).deleteWardById(Mockito.any(Long.class));
    }


    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void saveExamTEST() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/saveExamination")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"examinationName\":\"CD\",\"examinationId\":\"1\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(examinationService).saveExamination(Mockito.any(Examination.class));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void deleteExaminationByIdTEST() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteExaminationById/1"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(examinationService).deleteExaminationById(Mockito.any(Long.class));
    }

    @Test
    @WithMockUser(username="admin", password="123", roles={"ADMIN"})
    void saveUserTEST() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/saveUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"Thomas\",\"userId\":\"1\",\"password\":\"123\",\"role\":\"WORKER\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(302));

        Mockito.verify(customUserDetailsService).saveUser(Mockito.any(User.class));
    }

    @Test
    void loginTEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }





}
