package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String id;

    private String email;
    private String password;
    private String name;
    private String gender;

    private String dropYN;

    private LocalDateTime lastLoginTime;

    private LocalDateTime registerTime;

    private LocalDateTime modifyTime;

    public User toEntity() {
        return User
                .builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .gender(gender)
                .dropYN("N")
                .lastLoginTime(LocalDateTime.now())
                .registerTime(LocalDateTime.now())
                .modifyTime(LocalDateTime.now())
                .build();
    }
}
