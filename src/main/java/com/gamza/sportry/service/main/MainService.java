package com.gamza.sportry.service.main;

import com.gamza.sportry.dto.main.response.MainHotPostResponseDto;
import com.gamza.sportry.dto.main.response.MainUrgentPostResponseDto;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.custom.PostState;
import com.gamza.sportry.repo.PostRepo;
import java.util.Collections;
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

    public List<MainUrgentPostResponseDto> getUrgentPostList() {
        List<PostEntity> urgentPosts = postRepository.findAllByPostState(PostState.ABOUT);

        Collections.shuffle(urgentPosts);

        return urgentPosts.stream()
                .limit(10)
                .map(post -> MainUrgentPostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .tags(post.getPostTags().stream()
                                .map(postTag -> postTag.getTag().getName())
                                .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());
    }
}
