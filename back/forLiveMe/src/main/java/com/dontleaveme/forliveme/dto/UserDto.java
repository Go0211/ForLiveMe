package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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

    @Builder
    public UserDto(String id, String email, String password, String name,String gender , String dropYN, LocalDateTime lastLoginTime, LocalDateTime registerTime, LocalDateTime modifyTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dropYN = dropYN;
        this.lastLoginTime = lastLoginTime;
        this.registerTime = registerTime;
        this.modifyTime = modifyTime;
    }

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
