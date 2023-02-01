package com.dontleaveme.forliveme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    @GetMapping({"/simpleTest"})
    public String simpleTestStart(Model model, Authentication authentication) {
        log.info("testStart");

        model.addAttribute("userInfo", authentication.getName());
        model.addAttribute("test", new ArrayList<String>());

        return "/simpleTest/testPage";
    }

    @PostMapping({"/simpleTest"})
    public String simpleTestPost(Model model, Authentication authentication,
                                 @ModelAttribute("test") ArrayList<String> test) {
        log.info(test.get(0));
        log.info("testFinish");

        return "/simpleTest/testPage";
    }
}