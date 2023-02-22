package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.dto.TermsDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.TermsRepository;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import com.dontleaveme.forliveme.persistence.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//  유저정보
    @Transactional
    public UserDto getUser(String email) {
        // Optional : NPE(NullPointerException) 방지
        com.dontleaveme.forliveme.persistence.model.User user = userRepository.findByEmail(email);

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .gender(user.getGender())
                .dropYN(user.getDropYN())
                .lastLoginTime(user.getLastLoginTime())
                .registerTime(user.getRegisterTime())
                .modifyTime(user.getModifyTime())
                .build();

        log.info(userDto.getId() + " " + userDto.getEmail() + " "
                + userDto.getPassword() + " " + userDto.getName() + " "
                + userDto.getGender() + " " + userDto.getDropYN() + " "
                + userDto.getLastLoginTime() + " " + userDto.getRegisterTime() + " "
                + userDto.getModifyTime());

        return userDto;
    }

//  회원정보 변경
    @Transactional
    public void updateUser(UserDto userDto, UserDto beforeUser) {
        beforeUser.setName(userDto.getName());
        beforeUser.setGender(userDto.getGender());

        log.info(beforeUser.getId() + " " + beforeUser.getEmail() + " "
                + beforeUser.getPassword() + " " + beforeUser.getName() + " "
                + beforeUser.getGender() + " " + beforeUser.getDropYN() + " "
                + beforeUser.getLastLoginTime() + " " + beforeUser.getRegisterTime() + " "
                + beforeUser.getModifyTime());

        userRepository.save(beforeUser.toEntity());
    }

//  회원가입 (동의 + 정보기입)
    @Transactional
    public void insertUser(UserDto userDto, TermsDto termsDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        termsDto.setEmail(userDto.getEmail());

        termsRepository.save(termsDto.toEntity());
        userRepository.save(userDto.toEntity());
    }

//  유저명수
    @Transactional
    public Long getUserCount() {
        return userRepository.count();
    }
}
