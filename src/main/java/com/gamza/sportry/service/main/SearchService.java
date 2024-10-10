package com.gamza.sportry.service.main;

import com.gamza.sportry.dto.main.response.SearchPostResponseDto;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.QPostEntity;
import com.gamza.sportry.entity.QPostTagEntity;
import com.gamza.sportry.entity.QTagEntity;
import com.gamza.sportry.repo.PostRepo;
import com.gamza.sportry.repo.PostTagRepo;
import com.gamza.sportry.repo.TagRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PostRepo postRepo;
    private final TagRepo tagRepo;
    private final PostTagRepo postTagRepo;
    private final JPAQueryFactory jpaQueryFactory;

    QPostEntity qPostEntity = QPostEntity.postEntity;
    QPostTagEntity qPostTagEntity = QPostTagEntity.postTagEntity;
    QTagEntity qTagEntity = QTagEntity.tagEntity;


    public Page<SearchPostResponseDto> searchPage(List<String> tags, Pageable pageable) {
        // 태그 조건 설정
        BooleanExpression tagCondition = null;
        if (tags != null && !tags.isEmpty()) {
            tagCondition = qPostEntity.postTags.any().tag.name.in(tags);
        }
        // 검색 쿼리 작성
        List<PostEntity> postEntities = jpaQueryFactory
                .selectFrom(qPostEntity)
                .where(tagCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = jpaQueryFactory
                .selectFrom(qPostEntity)
                .where(tagCondition)
                .fetchCount();

        // 엔티티 -> DTO 변환 및 페이지 생성
        List<SearchPostResponseDto> dtos = postEntities.stream()
                .map(SearchPostResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, total);
    }
}
