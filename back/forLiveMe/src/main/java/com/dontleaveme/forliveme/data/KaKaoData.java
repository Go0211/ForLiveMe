package com.dontleaveme.forliveme.data;

import org.springframework.stereotype.Component;

@Component
public class KaKaoData {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
