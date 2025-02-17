package com.gamza.sportry.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@AllArgsConstructor
public class TimeService {

    public String timeSet(LocalDateTime dayBefore) {
        long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());
        String word;

        if (gap == 0){
            word = "방금전";
        }else if (gap < 60) {
            word = gap + "분전";
        }else if (gap < 60 * 24){
            word = (gap/60) + "시간전";
        }else if (gap < 60 * 24 * 8) {
            word = (gap/60/24) + "일전";
        } else {
            word = dayBefore.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        } return word;
    }

}
