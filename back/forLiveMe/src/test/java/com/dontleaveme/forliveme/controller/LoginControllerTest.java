package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.model.User;
import com.dontleaveme.forliveme.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;
    private UserService userService;

    @Test
    @DisplayName("login Get")
    public void loginGetTest() throws Exception{
        this.mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("join Get")
    public void joinGetTest() throws Exception{
        this.mvc.perform(get("/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("join"));
    }

    @Test
    @DisplayName("join Post")
    public void joinPostTest() throws Exception{
        UserDto userDto = new UserDto(null,"123","123","123","w",null,null,null,null);

        this.mvc.perform(post("/join"))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/login"));
    }
}