package com.gamza.sportry.entity;

import com.gamza.sportry.core.entity.BaseEntity;
import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.entity.custom.PostState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "sport_id")
    private SportEntity sport;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostTagEntity> postTags;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostState postState;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int commentCount;

    public void update(PostRequestDto postRequestDto, SportEntity sport) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postState = postRequestDto.getPostState();
        this.sport = sport;
    }

    public void upLikeCount() {
        this.likeCount += 1;
    }

    public void downLikeCount() {
        this.likeCount -= 1;
    }

    public void upViewCount() {
        this.viewCount += 1;
    }

    public void upCommentCount() {
        this.commentCount += 1;
    }

    public void downCommentCount() {
        this.commentCount -= 1;
    }


}
