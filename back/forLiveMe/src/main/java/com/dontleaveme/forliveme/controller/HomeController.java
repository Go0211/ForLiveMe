package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import com.dontleaveme.forliveme.service.EmpathyBoardService;
import com.dontleaveme.forliveme.service.LetterService;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import com.dontleaveme.forliveme.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@Controller
@AllArgsConstructor
public class HomeController {

    private LetterService letterService;
    private EmpathyBoardService empathyBoardService;
    private UserService userService;


    @GetMapping({ "/", "/index" })
    public String index(Model model, Authentication authentication) {
        log.info("index");

        if (authentication != null) {
            model.addAttribute("userInfo", authentication.getName());
        }

        model.addAttribute("userTotalCount" ,userService.getUserCount());
        model.addAttribute("empathyBoardTotalCount" ,empathyBoardService.getEmpathyBoardCount());
        model.addAttribute("letterTotalCount" ,letterService.getLetterCount());

        model.addAttribute("nowTime", LocalDateTime.now());

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