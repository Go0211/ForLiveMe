package com.dontleaveme.forliveme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class OtherService {
    //작성시간 - 현재시간 => 현재시간 대비 시간
    public String timeCheck(LocalDateTime localDateTime) {
        LocalDateTime current = LocalDateTime.now();

        Duration checkTime = Duration.between(localDateTime, current);

        if (checkTime.toMinutes() < 60 ) {
            return checkTime.toMinutes() + "분 전";
        } else if (checkTime.toHours() < 24) {
            return checkTime.toHours() + "시간 전";
        } else if (checkTime.toHours() < 24*31) {
            return checkTime.toHours() + "일 전";
        } else if (checkTime.toHours() < 24*365) {
            return (checkTime.toHours() / (24*30)) + "달 전";
        } else {
            return (checkTime.toHours() / (24 * 365)) + "년 전";
        }
    }
}
