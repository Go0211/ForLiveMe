package com.dontleaveme.forliveme.data;

import lombok.Data;

@Data
public class PasswordCheck {
    private String beforePw;
    private String afterPw;
    private String oneMoreAfterPw;
}
