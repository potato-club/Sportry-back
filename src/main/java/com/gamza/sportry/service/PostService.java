package com.gamza.sportry.service;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import com.gamza.sportry.dto.post.CrewPostsResponseDto;
import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.dto.post.MainPostsResponseDto;
import com.gamza.sportry.dto.post.PostResponseDto;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.SportEntity;
import com.gamza.sportry.entity.UserEntity;
import com.gamza.sportry.repo.PostRepo;
import com.gamza.sportry.repo.sport.SportRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final SportRepo sportRepo;
    private final UserService userService;
    private final TagService tagService;

    public void createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new NotFoundException("로그인 후 게시글 작성이 가능합니다", ErrorCode.NOT_FOUND_EXCEPTION);

        PostEntity post = PostEntity.builder()
                .user(user)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .postState(postRequestDto.getPostState())
                .sport(sportRepo.findByName(postRequestDto.getSport()))
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();

        postRepo.save(post);

        List<String> tags = postRequestDto.getTag();
        tags.forEach(tag -> tagService.addTag(post, tag));
    }

    public List<MainPostsResponseDto> findMainPosts() {
        List<PostEntity> posts = postRepo.findAll();
        return posts.stream()
                .map(post -> MainPostsResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .sport(post.getSport().getName())
                        .likeCount(post.getLikeCount())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CrewPostsResponseDto> findCrewPosts() {
        List<PostEntity> posts = postRepo.findAll();
        return posts.stream()
                .map(post -> CrewPostsResponseDto.builder()
                        .id(post.getId())
                        .createdDate(post.getCreatedDate())
                        .title(post.getTitle())
                        .postState(post.getPostState().getTitle())
                        .sport(post.getSport().getName())
                        .viewCount(post.getViewCount())
                        .likeCount(post.getLikeCount())
                        .commentCount(post.getCommentCount())
                        .tag(tagService.findPostTag(post))
                        .build())
                .collect(Collectors.toList());
    }

    public PostResponseDto findPost(Long id) {
        PostEntity post = postRepo.findById(id).orElse(null);
        if (post == null)
            throw new NotFoundException("찾을 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);

        post.upViewCount();

        return PostResponseDto.builder()
                .createdDate(post.getCreatedDate())
                .title(post.getTitle())
                .content(post.getContent())
                .postState(post.getPostState().getTitle())
                .sport(post.getSport().getName())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .tag(tagService.findPostTag(post))
                .build();
    }

    public void updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new UnAuthorizedException("게시글 수정 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        PostEntity post = postRepo.findById(id).orElse(null);
        if (post == null)
            throw new NotFoundException("수정할 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        if (post.getUser() != user)
            throw new UnAuthorizedException("게시글 수정 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        SportEntity sport = sportRepo.findByName(postRequestDto.getSport());
        post.update(postRequestDto, sport);
        tagService.delTag(post);
        List<String> tags = postRequestDto.getTag();
        tags.forEach(tag -> tagService.addTag(post, tag));
    }

    public void deletePost(Long id, HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null)
            throw new UnAuthorizedException("게시글 삭제 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        PostEntity post = postRepo.findById(id).orElse(null);
        if (post == null)
            throw new NotFoundException("삭제할 수 없는 게시글입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        if (post.getUser() != user)
            throw new UnAuthorizedException("게시글 삭제 권한이 없습니다", ErrorCode.UNAUTHORIZED_EXCEPTION);

        postRepo.delete(post);
    }

}
