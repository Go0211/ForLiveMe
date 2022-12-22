package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LetterDto {
    private Long leNum;
    private String leContent;
    private String leUserEmail;
    private LocalDateTime leWriteDate;

    @Builder
    public LetterDto(Long leNum, String leContent, String leUserEmail, LocalDateTime leWriteDate) {
        this.leNum = leNum;
        this.leContent = leContent;
        this.leUserEmail = leUserEmail;
        this.leWriteDate = leWriteDate;
    }

    public Letter toEntity() {
        return Letter.builder()
                .leNum(leNum)
                .leContent(leContent)
                .leUserEmail(leUserEmail)
                .leWriteDate(LocalDateTime.now())
                .build();
    }
}
