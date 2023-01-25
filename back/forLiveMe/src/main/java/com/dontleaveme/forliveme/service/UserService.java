package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.TermsDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.TermsRepository;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void insertUser(UserDto userDto, TermsDto termsDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        termsDto.setEmail(userDto.getEmail());

        termsRepository.save(termsDto.toEntity());
        userRepository.save(userDto.toEntity());
    }

//    public void insertUserTerms(UserDto userDto ,TermsDto termsDto) {
//        termsDto.setEmail(userDto.getEmail());
//
//        termsRepository.save(termsDto.toEntity());
//    }

//    public void updateLastLoginTime(UserDto userDto) {
//        userDto.builder()
//                .lastLoginTime(LocalDateTime.now())
//                .build();
//        userRepository.save(userDto.toEntity());
//    }
}
