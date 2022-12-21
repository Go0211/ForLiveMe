package com.dontleaveme.forliveme.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class LetterController {

    @GetMapping("letterMyList")
    public String boardList(Model model, Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());
        return "/letter/letter_myList";
    }

    @GetMapping("letterWriteList")
    public String boardWrite(Model model, Authentication authentication) {
        return "/letter/letter_writeList";
    }
}
