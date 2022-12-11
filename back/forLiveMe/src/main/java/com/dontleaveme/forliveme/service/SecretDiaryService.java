package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.persistence.dao.SecretDiaryRepository;
import com.dontleaveme.forliveme.dto.SecretDiaryDto;
import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class SecretDiaryService {
    private final SecretDiaryRepository secretDiaryRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10000; // 한 페이지에 존재하는 게시글 수

    private SecretDiaryDto convertEntityToDto(SecretDiary secretDiary) {
        return SecretDiaryDto.builder()
                .sdNum(secretDiary.getSdNum())
                .sdTitle(secretDiary.getSdTitle())
                .sdContent(secretDiary.getSdContent())
                .sdUserEmail(secretDiary.getSdUserEmail())
                .sdWriteDate(secretDiary.getSdWriteDate())
                .build();
    }

    //일기 작성
    public void writeSecretDiary(SecretDiaryDto secretDiaryDto, String userName) {
        secretDiaryDto.setSdPassword(bCryptPasswordEncoder.encode(secretDiaryDto.getSdPassword()));
        secretDiaryDto.setSdUserEmail(userName);

        secretDiaryRepository.save(secretDiaryDto.toEntity());
    }

    @Transactional
    public List<SecretDiaryDto> getSecretDiaryList(Integer pageNum, String user) {
//        Page<SecretDiary> page = secretDiaryRepository.findAll(PageRequest.of(
//                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "sdWriteDate")));

        Page<SecretDiary> page = secretDiaryRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "sdWriteDate")));

        List<SecretDiary> boardEntities = page.getContent();
        List<SecretDiaryDto> secretDiaryDtoList = new ArrayList<>();

        for (SecretDiary secretDiary : boardEntities) {
            if (secretDiary.getSdUserEmail().equals(user)) {
                secretDiaryDtoList.add(this.convertEntityToDto(secretDiary));
            }
        }

        return secretDiaryDtoList;
    }

    @Transactional
    public SecretDiaryDto getSecretDiary(Long id) {
        // Optional : NPE(NullPointerException) 방지
        Optional<SecretDiary> secretDiaryWrapper = secretDiaryRepository.findById(id);
        SecretDiary secretDiary = secretDiaryWrapper.get();

        SecretDiaryDto secretDiaryDto = SecretDiaryDto.builder()
                .sdNum(secretDiary.getSdNum())
                .sdTitle(secretDiary.getSdTitle())
                .sdContent(secretDiary.getSdContent())
                .sdUserEmail(secretDiary.getSdUserEmail())
                .sdPassword(secretDiary.getSdPassword())
                .sdWriteDate(secretDiary.getSdWriteDate())
                .build();

        return secretDiaryDto;
    }

    @Transactional
    public Long savePost(SecretDiaryDto secretDiaryDto) {
        return secretDiaryRepository.save(secretDiaryDto.toEntity()).getSdNum();
    }
    @Transactional
    public SecretDiaryDto getUpdateSecretDiary(Long no, SecretDiaryDto secretDiaryDto) {

        SecretDiaryDto secretDiaryDtoAfter = this.getSecretDiary(no);

        secretDiaryDtoAfter.setSdTitle(secretDiaryDto.getSdTitle());
        secretDiaryDtoAfter.setSdContent(secretDiaryDto.getSdContent());

        return secretDiaryDtoAfter;
    }

    @Transactional
    public void deletePost(Long id) {
        secretDiaryRepository.deleteById(id);
    }

    // 검색 API
    @Transactional
    public List<SecretDiaryDto> searchPosts(String keyword) {
        List<SecretDiary> secretDiaryEntities = secretDiaryRepository.findBySdTitleContaining(keyword);
        List<SecretDiaryDto> secretDiaryDtoList = new ArrayList<>();

        if (secretDiaryEntities.isEmpty()) {
            return secretDiaryDtoList;
        }
        for (SecretDiary secretDiary : secretDiaryEntities) {
            secretDiaryDtoList.add(this.convertEntityToDto(secretDiary));
        }

        return secretDiaryDtoList;
    }


    // 페이징
    @Transactional
    public Long getUserSecretDiaryCount(String user) {
//    public Long getSecretDiaryCount() {
        return secretDiaryRepository.countBySdUserEmail(user);
//        return secretDiaryRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum, String user) {
//        public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getUserSecretDiaryCount(user));
//        Double postsTotalCount = Double.valueOf(this.getUserSecretDiaryCount(user));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
}
