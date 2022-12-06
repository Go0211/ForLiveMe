package com.dontleaveme.forliveme.Controller;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import lombok.AllArgsConstructor;
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
    public String secretDiaryWrite(Model model) {
        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
        return "/secretDiary/secretDiary_write";
    }

    @PostMapping("/secretDiaryWrite")
    public String secretDiaryWrite(@ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto) {
        secretDiaryService.insert(secretDiaryDto);
        return "/secretDiary/secretDiary_list";
    }
}
