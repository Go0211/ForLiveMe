package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void insert(UserDto userDto) {
        userDto.builder()
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .build();
        userRepository.save(userDto.toEntity());
    }

    public void updateLastLoginTime(UserDto userDto) {
        userDto.builder()
                .lastLoginTime(LocalDateTime.now())
                .build();
        userRepository.save(userDto.toEntity());
    }
}
