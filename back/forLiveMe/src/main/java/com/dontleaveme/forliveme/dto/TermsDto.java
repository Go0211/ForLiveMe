package com.dontleaveme.forliveme.dto;

import com.dontleaveme.forliveme.persistence.model.Terms;
import com.dontleaveme.forliveme.persistence.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TermsDto {
    private String email;
    private boolean termsUse;
    private boolean personalInfo;
    private boolean locationInfo;
    private boolean eventSend;

    @Builder
    public TermsDto(String email, boolean termsUse, boolean personalInfo,
                 boolean locationInfo, boolean eventSend) {
        this.email = email;
        this.termsUse = termsUse;
        this.personalInfo = personalInfo;
        this.locationInfo = locationInfo;
        this.eventSend = eventSend;
    }

    public Terms toEntity() {
        return Terms.builder()
                .email(email)
                .termsUse(termsUse)
                .personalInfo(personalInfo)
                .locationInfo(locationInfo)
                .eventSend(eventSend)
                .build();
    }
}
