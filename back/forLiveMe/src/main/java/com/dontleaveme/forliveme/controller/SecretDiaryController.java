package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.service.OtherService;
import com.dontleaveme.forliveme.service.SecretDiaryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class SecretDiaryController {

    private SecretDiaryService secretDiaryService;

    private OtherService otherService;

    //비밀일기 작성
    @GetMapping("/secretDiaryWrite")
    public String secretDiaryWrite(Model model, Authentication authentication) {
        log.info("SD_Write_Start");
        model.addAttribute("secretDiaryDto" , new SecretDiaryDto());
        model.addAttribute("userInfo" , authentication.getName());
        return "/secretDiary/secretDiary_write";
    }

    @PostMapping("/secretDiaryWrite")
    public String secretDiaryWrite(@ModelAttribute("secretDiaryDto") SecretDiaryDto secretDiaryDto,
                                   HttpServletRequest request,
                                   Model model,
                                   Authentication authentication) {
        log.info("SD_Write_Post");

        secretDiaryService.writeSecretDiary(secretDiaryDto, authentication.getName());
        model.addAttribute("userInfo" , authentication.getName());

        log.info("SD_Write_Finish");
        return "redirect:/secretDiaryList";
    }

    //비밀일기 목록
    @GetMapping({"/secretDiaryList"})
    public String secretDiaryList(Model model, Authentication authentication,
                       @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        log.info("SD_List");

        model.addAttribute("userInfo" , authentication.getName());
        
        List<SecretDiaryDto> secretDiaryList = secretDiaryService.getSecretDiaryList(pageNum, authentication.getName());
        Integer[] pageList = secretDiaryService.getPageList(pageNum, authentication.getName());
        List<String> timeCheckList = new ArrayList<>();

        for (int i = 0; i < secretDiaryList.size(); i++) {
            timeCheckList.add(otherService.timeCheck(secretDiaryList.get(i).getSdWriteDate()));
        }

        model.addAttribute("secretDiaryList", secretDiaryList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("timeCheckList", timeCheckList);

        return "/secretDiary/secretDiary_list";
    }

    //비밀일기 보기
    @GetMapping("/secretDiaryList/{no}")
    public String secretDiaryView(@PathVariable("no") Long no, Model model,
                         Authentication authentication) {
        log.info("SD_View");

        model.addAttribute("userInfo" , authentication.getName());

        SecretDiaryDto secretDiaryDto = secretDiaryService.getSecretDiary(no);

        model.addAttribute("secretDiaryDto", secretDiaryDto);
        return "secretDiary/secretDiary_view";
    }

    //비밀일기 수정
    @GetMapping("/secretDiaryList/update/{no}")
    public String secretDiaryUpdate(@PathVariable("no") Long no, Model model,
                       Authentication authentication) {
        log.info("SD_Update_Start");

        model.addAttribute("userInfo" , authentication.getName());
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
    
    //비밀일기 삭제
    @GetMapping("/secretDiaryList/delete/{no}")        //DeleteMapping사용했었음
    public String delete(@PathVariable("no") Long no) {
        log.info("SD_Delete_Start");

        secretDiaryService.deletePost(no);

        log.info("SD_Delete_Finish");

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
