package com.dontleaveme.forliveme.data;

import org.springframework.stereotype.Component;

@Component
public class SNSNameData {
    private String kakaoUsername;
    private String googleUsername;

    public void setKakaoUsername(String kakaoUsername) {
        this.kakaoUsername = kakaoUsername;
    }

    public String getKakaoUsername() {
        return kakaoUsername;
    }

    public void setGoogleUsername(String googleUsername) {
        this.googleUsername = googleUsername;
    }

    public String getGoogleUsername() {
        return googleUsername;
    }
}
