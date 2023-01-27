package com.dontleaveme.forliveme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
@Log4j2
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping({ "/", "/index" })
    public String index(Model model, Authentication authentication) {
        log.info("index");

        if (authentication != null) {
            model.addAttribute("userInfo", authentication.getName());
        }

        return "index";
    }
}