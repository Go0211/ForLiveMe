package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.service.SecretDiaryService;
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
public class SecretDiaryController {

    private SecretDiaryService secretDiaryService;

    //비밀일기 작성
    @GetMapping("/secretDiaryWrite")
    public String secretDiaryWrite(Model model, Authentication authentication) {
        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
        model.addAttribute("userInfo" , authentication.getName());
        return "/secretDiary/secretDiary_write";
    }

    @PostMapping("/secretDiaryWrite")
    public String secretDiaryWrite(@ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto,
                                   HttpServletRequest request,
                                   Model model,
                                   Authentication authentication) {

        secretDiaryService.writeSecretDiary(secretDiaryDto, authentication.getName());
        model.addAttribute("userInfo" , authentication.getName());
        return "redirect:/secretDiaryList";
    }

//    @GetMapping("/secretDiaryList")
//    public String secretDiaryList(Model model, Authentication authentication) {
////        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
//        model.addAttribute("userInfo" , authentication.getName());
//        return "/secretDiary/secretDiary_list";
//    }

    // 게시판

    // 게시글 목록
    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.
    @GetMapping({"/secretDiaryList"})
    public String secretDiaryList(Model model, Authentication authentication,
                       @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        model.addAttribute("userInfo" , authentication.getName());
        
        List<SecretDiaryDto> secretDiaryList = secretDiaryService.getSecretDiaryList(pageNum, authentication.getName());
        Integer[] pageList = secretDiaryService.getPageList(pageNum, authentication.getName());

        model.addAttribute("secretDiaryList", secretDiaryList);
        model.addAttribute("pageList", pageList);

        return "/secretDiary/secretDiary_list";
    }

//    // 글쓰는 페이지
//
//    @GetMapping("/post")
//    public String write() {
//        return "board/write";
//    }
//
//    // 글을 쓴 뒤 POST 메서드로 글 쓴 내용을 DB에 저장
//    // 그 후에는 /list 경로로 리디렉션해준다.
//
//    @PostMapping("/post")
//    public String write(BoardDto boardDto) {
//        secretDiaryService.savePost(boardDto);
//        return "redirect:/board/list";
//    }

    // 게시물 상세 페이지이며, {no}로 페이지 넘버를 받는다.
    // PathVariable 애노테이션을 통해 no를 받음

    @GetMapping("/secretDiaryList/{no}")
    public String secretDiaryView(@PathVariable("no") Long no, Model model,
                         Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        SecretDiaryDto secretDiaryDto = secretDiaryService.getSecretDiary(no);

        model.addAttribute("secretDiaryDto", secretDiaryDto);
        return "secretDiary/secretDiary_view";
    }

    // 게시물 수정 페이지이며, {no}로 페이지 넘버를 받는다.
    @GetMapping("/secretDiaryList/update/{no}")
    public String secretDiaryUpdate(@PathVariable("no") Long no, Model model,
                       Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        SecretDiaryDto secretDiaryDto = secretDiaryService.getSecretDiary(no);

        model.addAttribute("secretDiaryDto", secretDiaryDto);
        return "secretDiary/secretDiary_update";
    }

    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    @PostMapping("/secretDiaryList/update/{no}")            //PutMapping사용했었음
    public String secretDiaryUpdate(@PathVariable("no") Long no,
            @ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto) {

        SecretDiaryDto updateSecretDiaryDto = secretDiaryService.getUpdateSecretDiary(no, secretDiaryDto);

        secretDiaryService.savePost(updateSecretDiaryDto);

        return "redirect:/secretDiaryList/"+no;
    }

    // 게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.

    @GetMapping("/secretDiaryList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        secretDiaryService.deletePost(no);

        return "redirect:/secretDiaryList";
    }

    // 검색
    // keyword를 view로부터 전달 받고
    // Service로부터 받은 boardDtoList를 model의 attribute로 전달해준다.

    @GetMapping("/secretDiaryList/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model
                         ,Authentication authentication) {
        model.addAttribute("userInfo" , authentication.getName());

        List<SecretDiaryDto> secretDiaryDtoList = secretDiaryService.searchPosts(keyword);

        model.addAttribute("secretDiaryDtoList", secretDiaryDtoList);

        return "secretDiary/secretDiary_list";
    }
}
