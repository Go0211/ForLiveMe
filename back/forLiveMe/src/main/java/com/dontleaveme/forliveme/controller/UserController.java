package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.data.PasswordCheck;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.service.EmpathyBoardService;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import com.dontleaveme.forliveme.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Log4j2
@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    private final EmpathyBoardService empathyBoardService;

    private final SecretDiaryService secretDiaryService;

//  마이페이지
    @GetMapping({ "/myPage" })
    public String myPage(Model model, Authentication authentication) {
        log.info("myPage");

        model.addAttribute("userName", authentication.getName());
        model.addAttribute("userInfo", userService.getUser(authentication.getName()));
        model.addAttribute("updateUserPw", new PasswordCheck());
        model.addAttribute("secretDiaryList",
                secretDiaryService.getSecretDiaryList(1, authentication.getName()));
        model.addAttribute("empathyBoardList",
                empathyBoardService.getEmpathyBoardList(1, authentication.getName()));

        return "myPage/myPage";
    }

//  유저정보 수정
    @PostMapping({"/myPage/updateUserInfo"})
    public String updateUserInfo(@ModelAttribute("updateUserInfo") UserDto updateUserInfo,
                                 Authentication authentication) {
        log.info("updateUserInfo");

        UserDto beforeUser = userService.getUser(authentication.getName());

        userService.updateUser(updateUserInfo, beforeUser);

        return "redirect:/myPage";
    }

//  비밀번호 변경
    @PostMapping({"/myPage/updateUserPw"})
    public String updateUserPw(@ModelAttribute("updateUserPw") PasswordCheck updateUserPw,
                                 Authentication authentication) {
        log.info("updateUserInfo");

        userService.updatePw(updateUserPw, authentication.getName());

        return "redirect:/myPage";
    }
}
