package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void insert(UserDto userDto) {
//        userDto.builder()
//                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
//                .build();

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(userDto.toEntity());
    }

//    public void updateLastLoginTime(UserDto userDto) {
//        userDto.builder()
//                .lastLoginTime(LocalDateTime.now())
//                .build();
//        userRepository.save(userDto.toEntity());
//    }
}
