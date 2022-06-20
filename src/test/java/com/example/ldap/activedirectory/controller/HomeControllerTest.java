package com.example.ldap.activedirectory.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getHomeUrl3xxRedirection() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("professor","professor");
        this.mockMvc.perform(get("/").headers(headers))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void getLogs() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        this.mockMvc.perform(get("/logs").headers(headers))
                .andExpect(status().isOk());
    }
    /*
    @Test
    void getHomeUrl() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("professor","professor");
        //this.mockMvc.perform(get("/").headers(headers))
          //      .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("professor", "professor")))
                .andExpect(MockMvcResultMatchers.content().string("Testing ldap / active directory authentication"));
    }
    @Test
    void getInvalid() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("a","aa");
        this.mockMvc.perform(get("/aa").headers(headers))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void getHomeUrlWithInvalidAuth() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("professor3","professor3");
        this.mockMvc.perform(get("/").headers(headers))
                .andExpect(status().is4xxClientError());
    }

   */
}
