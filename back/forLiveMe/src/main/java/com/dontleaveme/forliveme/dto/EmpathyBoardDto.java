package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.EmpathyBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class EmpathyBoardDto {
    private Long ebNum;

    private String ebTitle;

    private String ebContent;

    private String ebUserEmail;

    private LocalDateTime ebWriteDate;

    private int ebGood;

    private int ebView;

    @Builder
    public EmpathyBoardDto(Long ebNum, String ebTitle, String ebContent, String ebUserEmail,
                        LocalDateTime ebWriteDate, int ebGood, int ebView) {
        this.ebNum = ebNum;
        this.ebTitle = ebTitle;
        this.ebContent = ebContent;
        this.ebUserEmail = ebUserEmail;
        this.ebWriteDate = ebWriteDate;
        this.ebGood = ebGood;
        this.ebView = ebView;
    }

    public EmpathyBoard toEntity() {
        return EmpathyBoard.builder()
                .ebNum(ebNum)
                .ebTitle(ebTitle)
                .ebContent(ebContent)
                .ebUserEmail(ebUserEmail)
                .ebWriteDate(LocalDateTime.now())
                .ebGood(ebGood)
                .ebView(ebView)
                .build();
    }
}
