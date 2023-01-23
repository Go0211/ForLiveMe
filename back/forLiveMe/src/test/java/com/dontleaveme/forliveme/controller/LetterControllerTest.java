package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.LetterDto;
import com.dontleaveme.forliveme.service.LetterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LetterControllerTest {

    @Autowired
    private MockMvc mvc;
    private LetterService letterService;

    @Test
    @DisplayName("letterMyList Get")
    public void letterMyListTest() throws Exception{

        List<LetterDto> letterList = letterService.getMyLetterList(1, "`");
        Integer[] pageList = letterService.getPageList(1, null);

        this.mvc.perform(get("/letterMyList"))
                .andExpect(status().isOk());
    }
}