package com.dontleaveme.forliveme.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Log4j2
@Controller
@AllArgsConstructor
public class UserController {

    @GetMapping({ "/myPage" })
    public String myPage(Model model, Authentication authentication) {
        log.info("myPage");

        model.addAttribute("userInfo", authentication.getName());

        return "myPage/myPage";
    }

    @GetMapping("/myPage/{infoChangeFrame}")
    public String myPage(@PathVariable("infoChangeFrame") String infoChangeFrame
                         , Model model) {
        if (infoChangeFrame == "myInfo") {
            model.addAttribute("chooseFrame", "myInfo");
        } else if (infoChangeFrame == "myContent") {
            model.addAttribute("chooseFrame", "myContent");
        } else if (infoChangeFrame == "pwChange"){
            model.addAttribute("chooseFrame", "pwChange");
        } else {
            model.addAttribute("chooseFrame", "myInfo");
        }

        return "redirect:/myPage";
    }
}
