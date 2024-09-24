package com.gamza.sportry.entity.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostState {
    ING("모집중"),
    ABOUT("모집임박"),
    END("모집완료");

    private final String title;
}
