package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.data.KaKaoData;
import com.dontleaveme.forliveme.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@Controller
@AllArgsConstructor
public class HomeController {

    private final SecretDiaryService secretDiaryService;
    private final EmpathyBoardService empathyBoardService;
    private final UserService userService;

    @Autowired
    KaKaoData kd;

    @GetMapping({ "/", "/index" })
    public String index(Model model, Authentication authentication) {
        log.info("index");

        if ((authentication != null) && (kd.getUsername() == null)) {
            model.addAttribute("userName", authentication.getName());

            userService.updateLastLoginTime(authentication.getName());
        } else if (kd.getUsername() != null) {
            model.addAttribute("userName", kd.getUsername());
        }

        model.addAttribute("userTotalCount" ,userService.getUserCount());
        model.addAttribute("empathyBoardTotalCount" ,empathyBoardService.getEmpathyBoardCount());
        model.addAttribute("secretDiaryTotalCount" ,secretDiaryService.getSecretDiaryCount());

        model.addAttribute("nowTime", LocalDateTime.now());

        return "index";
    }

    @GetMapping({"/simpleTest"})
    public String simpleTestStart(Model model, Authentication authentication) {
        log.info("testStart");

        model.addAttribute("userName", authentication.getName());
        model.addAttribute("test", new ArrayList<String>());

        return "simpleTest/testPage";
    }

    @PostMapping({"/simpleTest"})
    public String simpleTestPost(Model model, Authentication authentication,
                                 @ModelAttribute("test") ArrayList<String> test) {
        log.info(test.get(0));
        log.info("testFinish");

        return "simpleTest/testPage";
    }
}