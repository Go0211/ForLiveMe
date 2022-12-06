package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.persistence.dao.SecretDiaryRepository;
import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecretDiaryService {
    private final SecretDiaryRepository secretDiaryRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void insert(SecretDiaryDto secretDiaryDto) {
//        secretDiaryDto.builder()
//                .sdPassword(bCryptPasswordEncoder.encode(secretDiaryDto.getSdPassword()))
//                .build();

        secretDiaryDto.setSdPassword(bCryptPasswordEncoder.encode(secretDiaryDto.getSdPassword()));

        secretDiaryRepository.save(secretDiaryDto.toEntity());
    }
}
