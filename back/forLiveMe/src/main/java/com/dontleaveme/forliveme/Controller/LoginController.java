package com.dontleaveme.forliveme.Controller;

import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("userDto") UserDto userDto) {
        userService.insert(userDto);
        return "redirect:/login";
    }
}


