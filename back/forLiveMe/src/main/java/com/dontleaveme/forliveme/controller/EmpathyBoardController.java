package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.EmpathyBoardDto;
import com.dontleaveme.forliveme.service.EmpathyBoardService;
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
public class EmpathyBoardController {

    private EmpathyBoardService empathyBoardService;

    //공감게시판 작성
    @GetMapping("/empathyBoardWrite")
    public String empathyBoardWrite(Model model, Authentication authentication) {
        log.info("EB_Write_Start");
        model.addAttribute("empathyBoardDto" , new EmpathyBoardDto());
        model.addAttribute("userInfo" , authentication.getName());
        return "/empathyBoard/empathyBoard_write";
    }

    @PostMapping("/empathyBoardWrite")
    public String empathyBoardWrite(@ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto,
                                    HttpServletRequest request,
                                    Model model,
                                    Authentication authentication) {
        log.info("EB_Write_Post");

        empathyBoardService.writeEmpathyBoard(empathyBoardDto, authentication.getName());
        model.addAttribute("userInfo" , authentication.getName());

        log.info("EB_Write_Finish");
        return "redirect:/empathyBoardList";
    }

    //공감게시판 목록
    @GetMapping({"/empathyBoardList"})
    public String empathyBoardList(Model model, Authentication authentication,
                                  @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("EB_List");

        model.addAttribute("userInfo" , authentication.getName());

        List<EmpathyBoardDto> empathyBoardList = empathyBoardService.getEmpathyBoardList(pageNum, authentication.getName());
        Integer[] pageList = empathyBoardService.getPageList(pageNum);

        model.addAttribute("empathyBoardList", empathyBoardList);
        model.addAttribute("pageList", pageList);

        return "/empathyBoard/empathyBoard_list";
    }

    //공감게시판 보기
    @GetMapping("/empathyBoardList/{no}")
    public String empathyBoardView(@PathVariable("no") Long no, Model model,
                                  Authentication authentication) {
        log.info("EB_View");

        model.addAttribute("userInfo" , authentication.getName());

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_view";
    }

    //공감게시판 수정
    @GetMapping("/empathyBoardList/update/{no}")
    public String empathyBoardUpdate(@PathVariable("no") Long no, Model model,
                                    Authentication authentication) {
        log.info("EB_Update_Start");

        model.addAttribute("userInfo" , authentication.getName());

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_update";
    }
    
    @PostMapping("/empathyBoardList/update/{no}")            //PutMapping사용했었음
    public String empathyBoardUpdate(@PathVariable("no") Long no,
                                    @ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto) {
        log.info("EB_Update_Post");

        EmpathyBoardDto updateEmpathyBoardDto = empathyBoardService.getUpdateEmpathyBoard(no, empathyBoardDto);

        empathyBoardService.savePost(updateEmpathyBoardDto);

        log.info("EB_Update_Finish");
        return "redirect:/empathyBoardList/"+no;
    }

    //공감게시판 삭제
    @GetMapping("/empathyBoardList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        log.info("EB_Delete_Start");

        empathyBoardService.deletePost(no);

        log.info("EB_Delete_Start");
        return "redirect:/empathyBoardList";
    }

    // 검색
    // keyword를 view로부터 전달 받고
    // Service로부터 받은 boardDtoList를 model의 attribute로 전달해준다.

    @GetMapping("/empathyBoardList/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model
            ,Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        List<EmpathyBoardDto> empathyBoardDtoList = empathyBoardService.searchPosts(keyword);

        model.addAttribute("empathyBoardDtoList", empathyBoardDtoList);

        return "empathyBoard/empathyBoard_list";
    }
}
