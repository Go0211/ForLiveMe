package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.TermsDto;
import com.dontleaveme.forliveme.dto.UserDto;
import com.dontleaveme.forliveme.persistence.dao.TermsRepository;
import com.dontleaveme.forliveme.persistence.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Long getUserCount() {
        return userRepository.count();
    }
}
