package com.dontleaveme.forliveme.security;

import com.dontleaveme.forliveme.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration{
    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* @formatter:off */
        http.authorizeRequests()
            .antMatchers("/", "/index", "/join", "/js/**" ,"/css/**", "/icon/**").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
            .anyRequest().authenticated(); // 그 외 모든 리소스를 의미하며 인증 필요

        http.formLogin()
            .permitAll()
            .loginPage("/login"); // 기본 로그인 페이지

        http.logout()
            .permitAll()
            // .logoutUrl("/logout") // 로그아웃 URL (기본 값 : /logout)
            // .logoutSuccessUrl("/login?logout") // 로그아웃 성공 URL (기본 값 : "/login?logout")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
            .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
            .invalidateHttpSession(true) // 로그아웃 시 세션 종료
            .clearAuthentication(true); // 로그아웃 시 권한 제거

        http.rememberMe() // rememberMe 기능 작동함
            .key("Gost")
            .rememberMeParameter("remember-me") // default: remember-me, checkbox 등의 이름과 맞춰야함
            .tokenValiditySeconds(3600) // 쿠키의 만료시간 설정(초), default: 14일
            .alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
            .userDetailsService(customUserDetailsService) // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설정 필요함.
            .tokenRepository(tokenRepository);

        return http.build();
        /* @formatter:on */
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}