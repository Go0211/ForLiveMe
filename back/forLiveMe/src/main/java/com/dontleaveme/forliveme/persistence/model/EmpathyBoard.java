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
@Table(name = "empathyboard")
public class EmpathyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long ebNum;
    @Column(name = "title")
    private String ebTitle;
    @Column(name = "content")
    private String ebContent;
    @Column(name = "useremail")
    private String ebUserEmail;
    @Column(name = "writedate")
    private LocalDateTime ebWriteDate;
    @Column(name = "good")
    private int ebGood;
    @Column(name = "view")
    private int ebView;

    @Builder
    public EmpathyBoard(Long ebNum, String ebTitle, String ebContent, String ebUserEmail,
                        LocalDateTime ebWriteDate, int ebGood, int ebView) {
        this.ebNum = ebNum;
        this.ebTitle = ebTitle;
        this.ebContent = ebContent;
        this.ebUserEmail = ebUserEmail;
        this.ebWriteDate = ebWriteDate;
        this.ebGood = ebGood;
        this.ebView = ebView;
    }
}
