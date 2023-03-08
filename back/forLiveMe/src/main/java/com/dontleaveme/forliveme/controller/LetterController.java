package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.LetterDto;
import com.dontleaveme.forliveme.service.LetterService;
import com.dontleaveme.forliveme.service.OtherService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class LetterController {
    private LetterService letterService;
    private OtherService otherService;

//  편지 목록
    @GetMapping("/letterMyList")
    public String letterMyList(Model model, Authentication authentication
            , @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("L_List");

        model.addAttribute("userName" , authentication.getName());

        List<LetterDto> letterList = letterService.getMyLetterList(pageNum, authentication.getName());
        Integer[] pageList = letterService.getPageList(pageNum, null);
        List<String> timeCheckList = new ArrayList<>();

        for (LetterDto letterDto : letterList) {
            timeCheckList.add(otherService.timeCheck(letterDto.getLeWriteDate()));
        }

        model.addAttribute("letterList", letterList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("timeCheckList", timeCheckList);

        return "letter/letter_myList";
    }

//  편지 작성
    @GetMapping("/letterWrite")
    public String letterWrite(Model model, Authentication authentication
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("L_Write_Start");

        model.addAttribute("letterDto" , new LetterDto());
        model.addAttribute("userName" , authentication.getName());

        List<LetterDto> letterList = letterService.getLetterList(pageNum);
        Integer[] pageList = letterService.getPageList(pageNum, null);

        model.addAttribute("letterList", letterList);
        model.addAttribute("pageList", pageList);

        return "letter/letter_writeList";
    }

    @PostMapping("/letterWrite")
    public String letterWrite(@ModelAttribute("letterDto") LetterDto letterDto,
                                    Model model,
                                    Authentication authentication) {
        log.info("L_Write_Post");

        letterService.writeLetter(letterDto, authentication.getName());
        model.addAttribute("userName" , authentication.getName());

        log.info("L_Write_Finish");
        return "redirect:/letterMyList";
    }

//  편지 삭제
    @GetMapping("/letterMyList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        log.info("L_Delete_Start");

        letterService.deletePost(no);

        log.info("L_Delete_Finish");
        return "redirect:/letterMyList";
    }
}
