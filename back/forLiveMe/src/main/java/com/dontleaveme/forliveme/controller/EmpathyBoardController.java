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

    //비밀일기 작성
    @GetMapping("/empathyBoardWrite")
    public String empathyBoardWrite(Model model, Authentication authentication) {
        model.addAttribute("empathyBoardDto" , new EmpathyBoardDto());
        model.addAttribute("userInfo" , authentication.getName());
        return "/empathyBoard/empathyBoard_write";
    }

    @PostMapping("/empathyBoardWrite")
    public String empathyBoardWrite(@ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto,
                                    HttpServletRequest request,
                                    Model model,
                                    Authentication authentication) {

        empathyBoardService.writeEmpathyBoard(empathyBoardDto, authentication.getName());
        model.addAttribute("userInfo" , authentication.getName());
        return "redirect:/empathyBoardList";
    }
    // 게시판
    // 게시글 목록
    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.
    @GetMapping({"/empathyBoardList"})
    public String empathyBoardList(Model model, Authentication authentication,
                                  @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        model.addAttribute("userInfo" , authentication.getName());

        List<EmpathyBoardDto> empathyBoardList = empathyBoardService.getEmpathyBoardList(pageNum, authentication.getName());
        Integer[] pageList = empathyBoardService.getPageList(pageNum);

        model.addAttribute("empathyBoardList", empathyBoardList);
        model.addAttribute("pageList", pageList);

        return "/empathyBoard/empathyBoard_list";
    }

    // 게시물 상세 페이지이며, {no}로 페이지 넘버를 받는다.
    // PathVariable 애노테이션을 통해 no를 받음
    @GetMapping("/empathyBoardList/{no}")
    public String empathyBoardView(@PathVariable("no") Long no, Model model,
                                  Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_view";
    }

    // 게시물 수정 페이지이며, {no}로 페이지 넘버를 받는다.
    @GetMapping("/empathyBoardList/update/{no}")
    public String empathyBoardUpdate(@PathVariable("no") Long no, Model model,
                                    Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        EmpathyBoardDto empathyBoardDto = empathyBoardService.getEmpathyBoard(no);

        model.addAttribute("empathyBoardDto", empathyBoardDto);
        return "empathyBoard/empathyBoard_update";
    }

    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    @PostMapping("/empathyBoardList/update/{no}")            //PutMapping사용했었음
    public String empathyBoardUpdate(@PathVariable("no") Long no,
                                    @ModelAttribute("empathyBoardDto") EmpathyBoardDto empathyBoardDto) {

        EmpathyBoardDto updateEmpathyBoardDto = empathyBoardService.getUpdateEmpathyBoard(no, empathyBoardDto);

        empathyBoardService.savePost(updateEmpathyBoardDto);

        return "redirect:/empathyBoardList/"+no;
    }

    // 게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.

    @GetMapping("/empathyBoardList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        empathyBoardService.deletePost(no);

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
