package com.gamza.sportry.controller;

import com.gamza.sportry.dto.main.request.SearchPostRequestDto;
import com.gamza.sportry.dto.main.response.SearchPostResponseDto;
import com.gamza.sportry.service.main.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
@Tag(name = "Main Controller", description = "메인 API")
public class MainController {
    private final SearchService searchService;

    @Operation(summary = "메인 검색 페이지")
    @GetMapping("/search")
    public Page<SearchPostResponseDto> searchPostList(
            @RequestParam(required = false) List<String> tags,
            @PageableDefault(size = 10) Pageable page){
                return searchService.searchPage(tags, page);
    }


}
