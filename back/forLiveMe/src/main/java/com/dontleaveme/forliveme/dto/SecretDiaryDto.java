package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.SecretDiary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SecretDiaryDto {

//    public User userId;

    private int sdNum;

    private String sdTitle;

    private String sdContent;

    private String sdUserEmail;

    private String sdPassword;

    private LocalDateTime sdWriteDate;

    @Builder
    public SecretDiaryDto(int sdNum, String sdTitle, String sdContent, String sdUserEmail, String sdPassword, LocalDateTime sdWriteDate) {
//        this.userId = userId;
        this.sdNum = sdNum;
        this.sdTitle = sdTitle;
        this.sdContent = sdContent;
        this.sdUserEmail = sdUserEmail;
        this.sdPassword = sdPassword;
        this.sdWriteDate = sdWriteDate;
    }

    public SecretDiary toEntity() {
        return SecretDiary.builder()
//                .userId(userId)
                .sdNum(sdNum)
                .sdTitle(sdTitle)
                .sdContent(sdContent)
                .sdUserEmail(sdUserEmail)
                .sdPassword(sdPassword)
                .sdWriteDate(LocalDateTime.now())
                .build();
    }
}
