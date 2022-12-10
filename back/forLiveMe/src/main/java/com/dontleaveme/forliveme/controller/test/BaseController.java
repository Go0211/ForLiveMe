package com.dontleaveme.forliveme.controller.test;

import com.dontleaveme.forliveme.persistence.model.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class BaseController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
}