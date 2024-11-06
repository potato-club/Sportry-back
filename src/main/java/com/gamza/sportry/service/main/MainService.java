package com.gamza.sportry.service.main;

import com.gamza.sportry.dto.main.response.MainHotPostResponseDto;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.repo.PostRepo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final PostRepo postRepository;

    public List<MainHotPostResponseDto> getHotPostList() {
        List<PostEntity> hotPosts = postRepository.findTop10ByOrderByViewCountDescLikeCountDesc();

        return hotPosts.stream()
                .map(post -> MainHotPostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .viewCount(post.getViewCount())
                        .postLikes(post.getLikeCount())
                        .commentCounts(post.getCommentCount())
                        .build())
                .collect(Collectors.toList());
    }
}
