package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.data.PasswordCheck;
import com.dontleaveme.forliveme.dto.TermsDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.TermsRepository;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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

        return userDto;
    }

//  회원정보 변경
    @Transactional
    public void updateUser(UserDto userDto, UserDto beforeUser) {
        beforeUser.setName(userDto.getName());
        beforeUser.setGender(userDto.getGender());

        beforeUser.setModifyTime(LocalDateTime.now());

        userRepository.save(beforeUser.toEntity());
    }

//  회원가입 (동의 + 정보기입)
    @Transactional
    public void insertUser(UserDto userDto, TermsDto termsDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setRegisterTime(LocalDateTime.now());
        userDto.setModifyTime(LocalDateTime.now());
        userDto.setLastLoginTime(LocalDateTime.now());

        termsDto.setEmail(userDto.getEmail());

        termsRepository.save(termsDto.toEntity());
        userRepository.save(userDto.toEntity());
    }


//  로그인 시간 갱신
    @Transactional
    public void updateLastLoginTime(String email) {
        UserDto userDto = this.getUser(email);

        userDto.setLastLoginTime(LocalDateTime.now());

        userRepository.save(userDto.toEntity());
    }

//  비밀번호 변경
    @Transactional
    public boolean updatePw(PasswordCheck updateUserPw, String email) {
        boolean beforePwCheck =
                bCryptPasswordEncoder.matches(updateUserPw.getBeforePw()
                        , this.getUser(email).getPassword());

        boolean afterPwCheck =
                updateUserPw.getAfterPw().equals(updateUserPw.getOneMoreAfterPw());

        if (beforePwCheck && afterPwCheck) {
            UserDto userDto = this.getUser(email);
            userDto.setPassword(
                    bCryptPasswordEncoder.encode(updateUserPw.getAfterPw())
            );

            userDto.setModifyTime(LocalDateTime.now());

            userRepository.save(userDto.toEntity());

            return true;
        }

        return false;
    }

//  유저 수
    @Transactional
    public Long getUserCount() {
        return userRepository.count();
    }

//    @Transactional
//    public String CheckLoginUser(String username) {
//        return userRepository.findByEmail(username).getEmail();
//    }
}
