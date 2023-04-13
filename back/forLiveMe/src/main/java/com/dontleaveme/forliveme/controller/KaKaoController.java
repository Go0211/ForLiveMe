package com.dontleaveme.forliveme.controller;

import com.dontleaveme.forliveme.data.KaKaoData;
import com.dontleaveme.forliveme.service.KaKaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class KaKaoController {
    //  이건 꼭 Autowired를 붙여줘야만 한다.
    @Autowired
    KaKaoService ks;
    @Autowired
    KaKaoData kd;

    @GetMapping("/kakao")
    public String getCI(@RequestParam String code, Model model) throws IOException {
        System.out.println("code = " + code);
        String access_token = ks.getToken(code);
        Map<String, Object> userInfo = ks.getUserInfo(access_token);
        
//      service를 따로 만들어서 해야할 듯
        kd.setUsername(userInfo.get("nickname").toString());
        
        //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
        return "redirect:/index";
    }
}