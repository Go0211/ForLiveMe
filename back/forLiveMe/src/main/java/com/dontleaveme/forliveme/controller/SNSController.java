package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.data.*;
import com.dontleaveme.forliveme.service.GoogleService;
import com.dontleaveme.forliveme.service.KaKaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(produces = "application/json")
public class SNSController {
    //  이건 꼭 Autowired를 붙여줘야만 한다.
    @Autowired
    KaKaoService kakaoService;
    @Autowired
    SNSNameData snsNameData;
    @Autowired
    GoogleService googleService;

    @GetMapping("/kakao")
    public String getCI(@RequestParam String code, Model model) throws IOException {
        System.out.println("code = " + code);
        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);

//      service를 따로 만들어서 해야할 듯
        snsNameData.setKakaoUsername(userInfo.get("nickname").toString());

        //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
        return "redirect:/index";
    }

    @GetMapping(value = "/google/login")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        return googleService.moveGoogleInitUrl();
    }

    @GetMapping(value = "/google/callback")
    public String redirectGoogleLogin(
            @RequestParam(value = "code") String authCode
    ) {
        ResponseEntity<GoogleLoginDto> googleResult
                = googleService.redirectGoogleLogin(authCode);

        snsNameData.setGoogleUsername(googleResult.getBody().getName());

        return "redirect:/index";
    }
}