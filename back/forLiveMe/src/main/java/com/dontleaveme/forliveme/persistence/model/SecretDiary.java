package com.dontleaveme.forliveme.persistence.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "secretdiary")
public class SecretDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long sdNum;
    @Column(name = "title")
    private String sdTitle;
    @Column(name = "content")
    private String sdContent;
    @Column(name = "useremail")
    private String sdUserEmail;
    @Column(name = "password")
    private String sdPassword;
    @Column(name = "writedate")
    private LocalDateTime sdWriteDate;

    @Builder
    public SecretDiary(Long sdNum, String sdTitle, String sdContent, String sdUserEmail, String sdPassword, LocalDateTime sdWriteDate) {
        this.sdNum = sdNum;
        this.sdTitle = sdTitle;
        this.sdContent = sdContent;
        this.sdUserEmail = sdUserEmail;
        this.sdPassword = sdPassword;
        this.sdWriteDate = sdWriteDate;
    }
}
