package com.dontleaveme.forliveme.service;

import com.dontleaveme.forliveme.dto.EmpathyBoardDto;
import com.dontleaveme.forliveme.persistence.dao.EmpathyBoardRepository;
import com.dontleaveme.forliveme.persistence.model.EmpathyBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmpathyBoardService {
    private final EmpathyBoardRepository empathyBoardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 10; // 한 페이지에 존재하는 게시글 수

    private static final int PAGE_TEN_MILLION = 10000000;
    private EmpathyBoardDto convertEntityToDto(EmpathyBoard empathyBoard) {
        return EmpathyBoardDto.builder()
                .ebNum(empathyBoard.getEbNum())
                .ebTitle(empathyBoard.getEbTitle())
                .ebContent(empathyBoard.getEbContent())
                .ebUserEmail(empathyBoard.getEbUserEmail())
                .ebWriteDate(empathyBoard.getEbWriteDate())
                .ebGood(empathyBoard.getEbGood())
                .ebView(empathyBoard.getEbView())
                .build();
    }

    //일기 작성
    public void writeEmpathyBoard(EmpathyBoardDto empathyBoardDto, String userName) {
        empathyBoardDto.setEbUserEmail(userName);

        empathyBoardRepository.save(empathyBoardDto.toEntity());
    }

    @Transactional
    public List<EmpathyBoardDto> getEmpathyBoardList(Integer pageNum, String user) {
        Page<EmpathyBoard> page = empathyBoardRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "ebWriteDate")));

        List<EmpathyBoard> empathyBoardEntities = page.getContent();
        List<EmpathyBoardDto> empathyBoardDtoList = new ArrayList<>();

        for (EmpathyBoard empathyBoard : empathyBoardEntities) {
            if (empathyBoard.getEbUserEmail().equals(user)) {
                empathyBoardDtoList.add(this.convertEntityToDto(empathyBoard));
            }
        }

        return empathyBoardDtoList;
    }

    @Transactional
    public EmpathyBoardDto getEmpathyBoard(Long id) {
        // Optional : NPE(NullPointerException) 방지
        Optional<EmpathyBoard> empathyBoardWrapper = empathyBoardRepository.findById(id);
        EmpathyBoard empathyBoard = empathyBoardWrapper.get();

        EmpathyBoardDto empathyBoardDto = EmpathyBoardDto.builder()
                .ebNum(empathyBoard.getEbNum())
                .ebTitle(empathyBoard.getEbTitle())
                .ebContent(empathyBoard.getEbContent())
                .ebUserEmail(empathyBoard.getEbUserEmail())
                .ebWriteDate(empathyBoard.getEbWriteDate())
                .ebGood(empathyBoard.getEbGood())
                .ebView(empathyBoard.getEbView())
                .build();

        return empathyBoardDto;
    }

    @Transactional
    public Long savePost(EmpathyBoardDto empathyBoardDto) {
        return empathyBoardRepository.save(empathyBoardDto.toEntity()).getEbNum();
    }

    @Transactional
    public EmpathyBoardDto getUpdateEmpathyBoard(Long no, EmpathyBoardDto empathyBoardDto) {

        EmpathyBoardDto empathyBoardDtoAfter = this.getEmpathyBoard(no);

        empathyBoardDtoAfter.setEbTitle(empathyBoardDto.getEbTitle());
        empathyBoardDtoAfter.setEbContent(empathyBoardDto.getEbContent());

        return empathyBoardDtoAfter;
    }

    @Transactional
    public void deletePost(Long id) {
        empathyBoardRepository.deleteById(id);
    }

    // 검색 API
    @Transactional
    public List<EmpathyBoardDto> searchPosts(String keyword) {
        List<EmpathyBoard> empathyBoardEntities = empathyBoardRepository.findByEbTitleContaining(keyword);
        List<EmpathyBoardDto> empathyBoardDtoList = new ArrayList<>();

        if (empathyBoardEntities.isEmpty()) {
            return empathyBoardDtoList;
        }
        for (EmpathyBoard empathyBoard : empathyBoardEntities) {
            empathyBoardDtoList.add(this.convertEntityToDto(empathyBoard));
        }

        return empathyBoardDtoList;
    }


    // 페이징
    @Transactional
    public Long getEmpathyBoardCount() {
        return empathyBoardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getEmpathyBoardCount());

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
