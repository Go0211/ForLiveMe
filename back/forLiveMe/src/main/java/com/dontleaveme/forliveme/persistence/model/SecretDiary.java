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

//    @Id
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name="userID", columnDefinition = "int", nullable = false)
//    public User userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long sdNum;
    @Column(name = "title")
    private String sdTitle;
    @Column(name = "content")
    private String sdContent;
    @Column(name = "password")
    private String sdPassword;
    @Column(name = "writedate")
    private LocalDateTime sdWriteDate;

    @Builder
    public SecretDiary(long sdNum, String sdTitle, String sdContent, String sdPassword, LocalDateTime sdWriteDate) {
//        this.userId = userId;
        this.sdNum = sdNum;
        this.sdTitle = sdTitle;
        this.sdContent = sdContent;
        this.sdPassword = sdPassword;
        this.sdWriteDate = sdWriteDate;
    }
}
