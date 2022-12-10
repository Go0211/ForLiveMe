package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SecretDiaryController {

    private SecretDiaryService secretDiaryService;

    @GetMapping("/secretDiaryWrite")
    public String secretDiaryWrite(Model model, Authentication authentication) {
        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
        model.addAttribute("userInfo", authentication.getName());
        return "/secretDiary/secretDiary_write";
    }

    @PostMapping("/secretDiaryWrite")
    public String secretDiaryWrite(@ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto,
                                   Authentication authentication) {
        secretDiaryDto.setSdUserEmail(authentication.getName());
        secretDiaryService.insert(secretDiaryDto);
        return "/secretDiary/secretDiary_list";
    }
}
