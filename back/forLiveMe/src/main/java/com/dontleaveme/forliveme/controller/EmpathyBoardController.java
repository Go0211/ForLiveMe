package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.EmpathyBoardDto;
import com.dontleaveme.forliveme.service.EmpathyBoardService;
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
public class EmpathyBoardController {
    private EmpathyBoardService empathyBoardService;
    private OtherService otherService;

//  공감게시판 작성
    @GetMapping("/empathyBoardWrite")
    public String empathyBoardWrite(Model model, Authentication authentication) {
        log.info("EB_Write_Start");
        model.addAttribute("empathyBoardDto" , new EmpathyBoardDto());
        model.addAttribute("userName" , authentication.getName());
        return "empathyBoard/empathyBoard_write";
    }

    @PostMapping("/empathyBoardWrite")
    public String empathyBoardWrite(@ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto,
                                    Model model,
                                    Authentication authentication) {
        log.info("EB_Write_Post");

        empathyBoardService.writeEmpathyBoard(empathyBoardDto, authentication.getName());
        model.addAttribute("userName" , authentication.getName());

        log.info("EB_Write_Finish");
        return "redirect:/empathyBoardList";
    }

//  공감게시판 목록
    @GetMapping({"/empathyBoardList"})
    public String empathyBoardList(Model model, Authentication authentication,
                                  @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("EB_List");

        model.addAttribute("userName" , authentication.getName());

        List<EmpathyBoardDto> empathyBoardList = empathyBoardService.getEmpathyBoardList(pageNum, authentication.getName());
        Integer[] pageList = empathyBoardService.getPageList(pageNum);

        List<String> timeCheckList = new ArrayList<>();
        for (EmpathyBoardDto empathyBoardDto : empathyBoardList) {
            timeCheckList.add(otherService.timeCheck(empathyBoardDto.getEbWriteDate()));
        }


        model.addAttribute("empathyBoardList", empathyBoardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("timeCheckList", timeCheckList);

        return "empathyBoard/empathyBoard_list";
    }

//  공감게시판 보기
    @GetMapping("/empathyBoardList/{no}")
    public String empathyBoardView(@PathVariable("no") Long no, Model model,
                                  Authentication authentication) {
        log.info("EB_View");

        model.addAttribute("userName" , authentication.getName());

        empathyBoardService.upViewCount(no);
        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_view";
    }

    @PostMapping("/empathyBoardList/{no}")
    public String empathyBoardGoodUp(@PathVariable("no") Long no, Model model,
                                     Authentication authentication) {
        model.addAttribute("userName" , authentication.getName());

        empathyBoardService.upGoodCount(no);

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);
        model.addAttribute("empathyBoardDto", empathyBoardDto);

        return "empathyBoard/empathyBoard_view";
    }

//  공감게시판 수정
    @GetMapping("/empathyBoardList/update/{no}")
    public String empathyBoardUpdate(@PathVariable("no") Long no, Model model,
                                    Authentication authentication) {
        log.info("EB_Update_Start");

        model.addAttribute("userName" , authentication.getName());

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_update";
    }
    
    @PostMapping("/empathyBoardList/update/{no}")
    public String empathyBoardUpdate(@PathVariable("no") Long no,
                                    @ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto) {
        log.info("EB_Update_Post");

        EmpathyBoardDto updateEmpathyBoardDto = empathyBoardService.getUpdateEmpathyBoard(no, empathyBoardDto);

        empathyBoardService.savePost(updateEmpathyBoardDto);

        log.info("EB_Update_Finish");
        return "redirect:/empathyBoardList/"+no;
    }

//  공감게시판 삭제
    @GetMapping("/empathyBoardList/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        log.info("EB_Delete_Start");

        empathyBoardService.deletePost(no);

        log.info("EB_Delete_Start");
        return "redirect:/empathyBoardList";
    }

//  검색
    @GetMapping("/empathyBoardList/search")
    public String search(@RequestParam(value="keyword") String keyword,
                         Model model, Authentication authentication) {
        model.addAttribute("userName" , authentication.getName());

        List<EmpathyBoardDto> empathyBoardDtoList = empathyBoardService.searchPosts(keyword);

        List<String> timeCheckList = new ArrayList<>();
        for (EmpathyBoardDto empathyBoardDto : empathyBoardDtoList) {
            timeCheckList.add(otherService.timeCheck(empathyBoardDto.getEbWriteDate()));
        }

        model.addAttribute("empathyBoardList", empathyBoardDtoList);
        model.addAttribute("timeCheckList", timeCheckList);


        return "empathyBoard/empathyBoard_list";
    }
}
