package com.dontleaveme.forliveme.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void login() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void joinTerms() {
    }

    @Test
    void joinGoUserInfo() {
    }

    @Test
    void joinUserInfo() {
    }

    @Test
    void join() {
    }
}