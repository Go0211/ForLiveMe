package com.dontleaveme.forliveme.persistence.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "terms")
public class Terms {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "termsuse")
    private boolean termsUse;

    @Column(name = "personalinfo")
    private boolean personalInfo;

    @Column(name = "locationinfo")
    private boolean locationInfo;

    @Column(name = "eventsend")
    private boolean eventSend;

    @Builder
    public Terms(String email, boolean termsUse, boolean personalInfo,
                 boolean locationInfo, boolean eventSend) {
        this.email = email;
        this.termsUse = termsUse;
        this.personalInfo = personalInfo;
        this.locationInfo = locationInfo;
        this.eventSend = eventSend;
    }
}