package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.LetterDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.service.UserService;
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

    private final UserService userService;

    @GetMapping({ "/myPage" })
    public String myPage(Model model, Authentication authentication) {
        log.info("myPage");

        model.addAttribute("userName", authentication.getName());
        model.addAttribute("userInfo", userService.getUser(authentication.getName()));

        return "myPage/myPage";
    }

    @PostMapping({"/myPage/updateUserInfo"})
    public String updateUserInfo(@ModelAttribute("updateUserInfo") UserDto updateUserInfo,
                                 Authentication authentication) {
        log.info("updateUserInfo");

        UserDto beforeUser = userService.getUser(authentication.getName());

        log.info(updateUserInfo.getId() + " " + updateUserInfo.getEmail() + " "
                + updateUserInfo.getPassword() + " " + updateUserInfo.getName() + " "
                + updateUserInfo.getGender() + " " + updateUserInfo.getDropYN() + " "
                + updateUserInfo.getLastLoginTime() + " " + updateUserInfo.getRegisterTime() + " "
                + updateUserInfo.getModifyTime());

        userService.updateUser(updateUserInfo, beforeUser);

        return "redirect:/myPage";
    }
}
