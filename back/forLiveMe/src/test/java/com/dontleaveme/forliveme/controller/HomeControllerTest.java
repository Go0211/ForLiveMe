package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class HomeControllerTest {

    @Autowired
    HomeController homeController;
    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void index() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

//    @Test
//    void simpleTestStart() throws Exception{
//        String username = userRepository.findByEmail("`").getEmail();
//        ArrayList<String> test = new ArrayList<String>();
//
//        mockMvc.perform(get("/simpleTest"))
//                .andExpect(model().attribute("userName", username))
//                .andExpect(model().attribute("test", test))
////                .andExpect(view().name("simpleTest/testPage"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void simpleTestPost() throws Exception{
//
//    }
}