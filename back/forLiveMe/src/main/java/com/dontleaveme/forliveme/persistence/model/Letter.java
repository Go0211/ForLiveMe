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
@Table(name = "letter")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long leNum;
    @Column(name = "content")
    private String leContent;
    @Column(name = "useremail")
    private String leUserEmail;
    @Column(name = "writedate")
    private LocalDateTime leWriteDate;

    @Builder
    public Letter(Long leNum, String leContent, String leUserEmail, LocalDateTime leWriteDate) {
        this.leNum = leNum;
        this.leContent = leContent;
        this.leUserEmail = leUserEmail;
        this.leWriteDate = leWriteDate;
    }
}
