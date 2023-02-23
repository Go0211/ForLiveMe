package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.data.PasswordCheck;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Log4j2
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({ "/myPage" })
    public String myPage(Model model, Authentication authentication) {
        log.info("myPage");

        model.addAttribute("userName", authentication.getName());
        model.addAttribute("userInfo", userService.getUser(authentication.getName()));
        model.addAttribute("updateUserPw", new PasswordCheck());

        return "myPage/myPage";
    }

    @PostMapping({"/myPage/updateUserInfo"})
    public String updateUserInfo(@ModelAttribute("updateUserInfo") UserDto updateUserInfo,
                                 Authentication authentication) {
        log.info("updateUserInfo");

        UserDto beforeUser = userService.getUser(authentication.getName());

        userService.updateUser(updateUserInfo, beforeUser);

        return "redirect:/myPage";
    }

    @PostMapping({"/myPage/updateUserPw"})
    public String updateUserPw(@ModelAttribute("updateUserPw") PasswordCheck updateUserPw,
                                 Authentication authentication) {
        log.info("updateUserInfo");

        boolean updatePwCheck =
                userService.updatePw(updateUserPw, authentication.getName());

        if (updatePwCheck) {
            return "redirect:/myPage";
        }

        return "redirect:/myPage";
    }
}
