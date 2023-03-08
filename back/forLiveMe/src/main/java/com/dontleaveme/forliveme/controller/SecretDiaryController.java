package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.service.OtherService;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class SecretDiaryController {
    private SecretDiaryService secretDiaryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private OtherService otherService;

//  비밀일기 작성
    @GetMapping("/secretDiaryWrite")
    public String writeSecretDiary(Model model, Authentication authentication) {
        log.info("SD_Write_Start");

        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
        model.addAttribute("userName" , authentication.getName());

        return "secretDiary/secretDiary_write";
    }
    @PostMapping("/secretDiaryWrite")
    public String writeSecretDiary(@ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto,
                                   Model model,
                                   Authentication authentication) {
        log.info("SD_Write_Post");

        secretDiaryService.writeSecretDiary(secretDiaryDto, authentication.getName());
        model.addAttribute("userName" , authentication.getName());

        log.info("SD_Write_Finish");
        return "redirect:/secretDiaryList";
    }

//  비밀일기 목록
    @GetMapping({"/secretDiaryList"})
    public String secretDiaryList(Model model,
                                  Authentication authentication,
                                  @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("SD_List");

        model.addAttribute("userName" , authentication.getName());
        
        List<SecretDiaryDto> secretDiaryList = secretDiaryService.getSecretDiaryList(pageNum, authentication.getName());
        Integer[] pageList = secretDiaryService.getPageList(pageNum, authentication.getName());

        List<String> timeCheckList = new ArrayList<>();
        for (SecretDiaryDto secretDiaryDto : secretDiaryList) {
            timeCheckList.add(otherService.timeCheck(secretDiaryDto.getSdWriteDate()));
        }

        model.addAttribute("secretDiaryList", secretDiaryList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("timeCheckList", timeCheckList);

        return "secretDiary/secretDiary_list";
    }

//  비밀일기 비밀번호 해제
    @GetMapping("/secretDiaryList/before/{no}")
    public String secretDiaryViewBefore(Model model, Authentication authentication,
                                        @PathVariable("no") Long no) {
        model.addAttribute("userName" , authentication.getName());
        model.addAttribute("num", no);

        return "secretDiary/secretDiary_view_before";
    }

    @PostMapping("/secretDiaryList/before/{no}")
    public String secretDiaryViewBefore(Model model, Authentication authentication,
                                        @ModelAttribute("password") String pw,
                                        @PathVariable("no") Long no) {

        model.addAttribute("userName" , authentication.getName());

        String sDPassword = secretDiaryService.getPassword(no);
        boolean check = bCryptPasswordEncoder.matches(pw, sDPassword);

        if (check) {
            return "redirect:/secretDiaryList/"+no;
        } else {
            return "redirect:/secretDiaryList/before/"+no;
        }
    }

//  비밀일기 보기
    @GetMapping("/secretDiaryList/{no}")
    public String secretDiaryView(@PathVariable("no") Long no, Model model,
                         Authentication authentication) {
        log.info("SD_View");

        model.addAttribute("userName" , authentication.getName());

        SecretDiaryDto secretDiaryDto = secretDiaryService.getSecretDiary(no);

        model.addAttribute("secretDiaryDto", secretDiaryDto);
        return "secretDiary/secretDiary_view";
    }

//  비밀일기 수정
    @GetMapping("/secretDiaryList/update/{no}")
    public String secretDiaryUpdate(@PathVariable("no") Long no, Model model,
                       Authentication authentication) {
        log.info("SD_Update_Start");

        model.addAttribute("userName" , authentication.getName());
        SecretDiaryDto secretDiaryDto = secretDiaryService.getSecretDiary(no);
        model.addAttribute("secretDiaryDto", secretDiaryDto);

        return "secretDiary/secretDiary_update";
    }
    
    @PostMapping("/secretDiaryList/update/{no}")            //PutMapping사용했었음
    public String secretDiaryUpdate(@PathVariable("no") Long no,
            @ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto) {
        log.info("SD_Update_Post");

        SecretDiaryDto updateSecretDiaryDto = secretDiaryService.getUpdateSecretDiary(no, secretDiaryDto);
        secretDiaryService.savePost(updateSecretDiaryDto);

        log.info("SD_Update_Finish");

        return "redirect:/secretDiaryList/"+no;
    }
    
//  비밀일기 삭제
    @GetMapping("/secretDiaryList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        log.info("SD_Delete_Start");

        secretDiaryService.deletePost(no);

        log.info("SD_Delete_Finish");

        return "redirect:/secretDiaryList";
    }

//  검색
    @GetMapping("/secretDiaryList/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model
                         ,Authentication authentication) {

        model.addAttribute("userName" , authentication.getName());

        List<SecretDiaryDto> secretDiaryDtoList = secretDiaryService.searchPosts(keyword);

        List<String> timeCheckList = new ArrayList<>();
        for (SecretDiaryDto secretDiaryDto : secretDiaryDtoList) {
            timeCheckList.add(otherService.timeCheck(secretDiaryDto.getSdWriteDate()));
        }

        model.addAttribute("secretDiaryList", secretDiaryDtoList);
        model.addAttribute("timeCheckList", timeCheckList);

        return "secretDiary/secretDiary_list";
    }
}
