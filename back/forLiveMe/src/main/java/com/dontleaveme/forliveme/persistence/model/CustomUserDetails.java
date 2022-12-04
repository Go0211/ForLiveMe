package com.dontleaveme.forliveme.persistence.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails, Serializable {
    private String id;	// DB에서 PK 값
    private String email;	//이메일
    private String password;	// 비밀번호
    private String gender;	//이메일 인증 여부
    private String drop_yn;	//계정 잠김 여부
    private LocalDateTime lastLoginTime;	//최근로그인
    private LocalDateTime registerTime;     //가입날짜
    private LocalDateTime modifyTime;
    private Collection<GrantedAuthority> authorities;	//권한 목록 ??

    @Override // 해당 유저의 권한 목록
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override //계정 만료 여부 t : 만료안됨 , f : 만료
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠김 여부 t : 잠기지 않음 , f : 잠김
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override // 비밀번호 만료 여부 t : 만료안됨 , f : 만료
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 사용자 활성화 여부 t : 활성화 , f : 비활성화
    public boolean isEnabled() {
        return true;
    }
}
