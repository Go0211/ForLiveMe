package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.annotation.LoginUser;
import com.dontleaveme.forliveme.persistence.model.CustomUserDetails;
import com.dontleaveme.forliveme.persistence.model.SessionUser;
import com.dontleaveme.forliveme.persistence.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession httpSession;

    @GetMapping({ "/", "/index" })
    public String index(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userInfo", authentication.getName());
        }

        return "index";
    }

    @GetMapping("/test")
    public String test(Model model, Authentication authentication, Principal principal, @AuthenticationPrincipal User user) {

        log.info("user : " + user);
        log.info("Authentication : " + authentication.getPrincipal());
        log.info("principal : " + principal);

        model.addAttribute("user", authentication.getName());

        return "/test/test";
    }
}