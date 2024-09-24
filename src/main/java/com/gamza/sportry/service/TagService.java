package com.gamza.sportry.service;

import com.gamza.sportry.dto.post.PostRequestDto;
import com.gamza.sportry.entity.PostEntity;
import com.gamza.sportry.entity.PostTagEntity;
import com.gamza.sportry.entity.TagEntity;
import com.gamza.sportry.repo.PostTagRepo;
import com.gamza.sportry.repo.TagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepo tagRepo;
    private final PostTagRepo postTagRepo;

    public void addTag(PostEntity post, String addTag) {
        TagEntity tag = tagRepo.findByName(addTag);
        if (tag == null) {
            TagEntity newTag = TagEntity.builder()
                    .name(addTag)
                    .build();
            tagRepo.save(newTag);

            PostTagEntity postTag = PostTagEntity.builder()
                    .post(post)
                    .tag(newTag)
                    .build();
            postTagRepo.save(postTag);
        } else {
            PostTagEntity postTag = PostTagEntity.builder()
                    .post(post)
                    .tag(tag)
                    .build();
            postTagRepo.save(postTag);
        }
    }

    public void delTag(PostEntity post) {
        postTagRepo.deleteAllByPost(post);
    }

    public List<String> findPostTag(PostEntity post) {
        List<PostTagEntity> postTags = post.getPostTags();
        List<TagEntity> tags = postTags.stream().map(postTag -> postTag.getTag()).collect(Collectors.toList());
        List<String> tagNames = tags.stream().map(tag -> tag.getName()).collect(Collectors.toList());
        return tagNames;
    }

}
