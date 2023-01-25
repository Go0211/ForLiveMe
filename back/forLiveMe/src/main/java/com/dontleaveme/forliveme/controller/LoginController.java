package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.TermsDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@Log4j2
public class LoginController {

    private final UserService userService;

    public static TermsDto termsDto;

    @GetMapping("login")
    public String login(HttpServletRequest request) {
        return "/login";
    }

    @GetMapping("/join")
    public String joinTerms(Model model) {
        model.addAttribute("termsDto", new TermsDto());
        model.addAttribute("oneMore", false);
        log.info("termsIn");
        return "join/joinTerms";
    }

    @PostMapping("/join")
    public String joinGoUserInfo(Model model,
                                 @ModelAttribute("termsDto") TermsDto termsDtos) {
        termsDto = termsDtos;

        if (termsDto.isTermsUse() == false || termsDto.isPersonalInfo() == false) {
            log.info("needTermsFalse");
            model.addAttribute("termsDto", termsDto);
            model.addAttribute("oneMore", true);

            return "join/joinTerms";
        } else {
            log.info("needTermsTrue");
            return "redirect:/joinResult";
        }
    }

    @GetMapping("/joinResult")
    public String joinUserInfo(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/join/joinUserInfo";
    }

    @PostMapping("/joinResult")
    public String join(@ModelAttribute("userDto")  UserDto userDto){
        log.info(userDto.getEmail());
        userService.insertUser(userDto, termsDto);
        return "redirect:/login";
    }
}


