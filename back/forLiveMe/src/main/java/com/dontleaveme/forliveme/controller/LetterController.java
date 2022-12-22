package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.EmpathyBoardDto;
import com.dontleaveme.forliveme.dto.LetterDto;
import com.dontleaveme.forliveme.service.LetterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class LetterController {

    private LetterService letterService;

    @GetMapping("/letterMyList")
    public String letterMyList(Model model, Authentication authentication
            , @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        model.addAttribute("userInfo" , authentication.getName());

        List<LetterDto> letterList = letterService.getMyLetterList(pageNum, authentication.getName());
        Integer[] pageList = letterService.getPageList(pageNum, null);

        model.addAttribute("letterList", letterList);
        model.addAttribute("pageList", pageList);

        return "/letter/letter_myList";
    }

    //비밀일기 작성
    @GetMapping("/letterWrite")
    public String letterWrite(Model model, Authentication authentication
            ,@RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        model.addAttribute("letterDto" , new LetterDto());
        model.addAttribute("userInfo" , authentication.getName());

        List<LetterDto> letterList = letterService.getLetterList(pageNum);
        Integer[] pageList = letterService.getPageList(pageNum, null);

        model.addAttribute("letterList", letterList);
        model.addAttribute("pageList", pageList);

        return "/letter/letter_writeList";
    }

    @PostMapping("/letterWrite")
    public String letterWrite(@ModelAttribute("letterDto") LetterDto letterDto,
                                    HttpServletRequest request,
                                    Model model,
                                    Authentication authentication) {

        letterService.writeLetter(letterDto, authentication.getName());
        model.addAttribute("userInfo" , authentication.getName());
        return "redirect:/letterMyList";
    }

    // 게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.

    @GetMapping("/letterMyList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        letterService.deletePost(no);

        return "redirect:/letterMyList";
    }

    // 검색
    // keyword를 view로부터 전달 받고
    // Service로부터 받은 boardDtoList를 model의 attribute로 전달해준다.

    @GetMapping("/letterMyList/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model
            ,Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        List<LetterDto> letterDtoList = letterService.searchPosts(keyword);

        model.addAttribute("letterDtoList", letterDtoList);

        return "letter/letter_myList";
    }
}
