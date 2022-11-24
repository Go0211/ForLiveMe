package com.dontleaveme.forliveme.persistence.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "drop_yn")
    private String dropYN;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "register_time")
    private LocalDateTime registerTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    @Builder
    public User(String id, String email, String password, String name,String gender , String dropYN, LocalDateTime lastLoginTime, LocalDateTime registerTime, LocalDateTime modifyTime) {
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
}