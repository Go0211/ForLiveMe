package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.LetterDto;
import com.dontleaveme.forliveme.persistence.dao.LetterRepository;
import com.dontleaveme.forliveme.persistence.model.Letter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10; // 한 페이지에 x  존재하는 게시글 수
    private static final int PAGE_TEN_MILLION = 10000000;

    private LetterDto convertEntityToDto(Letter letter) {
        return LetterDto.builder()
                .leNum(letter.getLeNum())
                .leContent(letter.getLeContent())
                .leUserEmail(letter.getLeUserEmail())
                .leWriteDate(letter.getLeWriteDate())
                .build();
    }

    //일기 작성
    public void writeLetter(LetterDto letterDto, String userName) {
        letterDto.setLeUserEmail(userName);

        letterRepository.save(letterDto.toEntity());
    }

    @Transactional
    public List<LetterDto> getMyLetterList(Integer pageNum, String user) {
        Page<Letter> page = letterRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_TEN_MILLION, Sort.by(Sort.Direction.ASC, "leWriteDate")));

        List<Letter> letterEntities = page.getContent();
        List<LetterDto> letterDtoList = new ArrayList<>();

        for (Letter letter : letterEntities) {
            if (letter.getLeUserEmail().equals(user)) {
                letterDtoList.add(this.convertEntityToDto(letter));
            }
        }

        Collections.reverse(letterDtoList);


        return letterDtoList;
    }

    @Transactional
    public List<LetterDto> getLetterList(Integer pageNum) {
        Page<Letter> page = letterRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "leWriteDate")));

        List<Letter> letterEntities = page.getContent();
        List<LetterDto> LetterDtoList = new ArrayList<>();

        for (Letter letter : letterEntities) {
            LetterDtoList.add(this.convertEntityToDto(letter));
        }

        return LetterDtoList;
    }

    @Transactional
    public LetterDto getLetter(Long id) {
        // Optional : NPE(NullPointerException) 방지
        Optional<Letter> LetterWrapper = letterRepository.findById(id);
        Letter letter = LetterWrapper.get();

        LetterDto letterDto = LetterDto.builder()
                .leNum(letter.getLeNum())
                .leContent(letter.getLeContent())
                .leUserEmail(letter.getLeUserEmail())
                .leWriteDate(letter.getLeWriteDate())
                .build();

        return letterDto;
    }

    @Transactional
    public Long savePost(LetterDto letterDto) {
        return letterRepository.save(letterDto.toEntity()).getLeNum();
    }

    @Transactional
    public LetterDto getUpdateLetter(Long no, LetterDto letterDto) {

        LetterDto letterDtoAfter = this.getLetter(no);

        letterDtoAfter.setLeContent(letterDtoAfter.getLeContent());

        return letterDtoAfter;
    }

    @Transactional
    public void deletePost(Long id) {
        letterRepository.deleteById(id);
    }

    // 검색 API
    @Transactional
    public List<LetterDto> searchPosts(String keyword) {
        List<Letter> letterEntities = letterRepository.findByLeContentContaining(keyword);
        List<LetterDto> letterDtoList = new ArrayList<>();

        if (letterEntities.isEmpty()) {
            return letterDtoList;
        }
        for (Letter letter : letterEntities) {
            letterDtoList.add(this.convertEntityToDto(letter));
        }

        return letterDtoList;
    }


    // 페이징
    @Transactional
    public Long getLetterCount() {
        return letterRepository.count();
    }

    @Transactional
    public Long getMyLetterCount(String userName) {
        return letterRepository.countByLeUserEmail(userName);
    }

    public Integer[] getPageList(Integer curPageNum, String userName) {
        Integer[] pageList;
        // 총 게시글 갯수
        Double postsTotalCount;
        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum;
        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum;

        // 총 게시글 갯수
        if (userName != null) {
            pageList = new Integer[PAGE_TEN_MILLION];
            postsTotalCount = Double.valueOf(this.getMyLetterCount(userName));
            totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_TEN_MILLION)));
            blockLastPageNum = (totalLastPageNum > curPageNum + PAGE_TEN_MILLION)
                    ? curPageNum + PAGE_TEN_MILLION
                    : totalLastPageNum;
        } else {
            pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
            postsTotalCount = Double.valueOf(this.getLetterCount());
            totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
            blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                    ? curPageNum + BLOCK_PAGE_NUM_COUNT
                    : totalLastPageNum;

        }

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
}
