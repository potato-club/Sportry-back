package com.gamza.sportry.entity;

import com.gamza.sportry.core.entity.BaseEntity;
import com.gamza.sportry.entity.custom.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostEntity> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CareerEntity> careers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClassEntity> classes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "town_id")
    private TownEntity town; // 사용자가 선택한 읍면동

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private UserRole userRole;

    private String gender;
    private LocalDate birth;

    public void updateRefreshToken(String RT) {
        this.refreshToken = RT;
    }
    public void setTown(TownEntity town) {
        // 중복 설정 방지
        if (this.town != null && this.town.getId().equals(town.getId())) {
            return; // 동일한 지역인 경우 설정하지 않고 종료
        }

        // 기존에 설정된 지역이 있을 경우 관계 해제
        if (this.town != null) {
            this.town.getUsers().remove(this);
        }

        this.town = town;

        // 새로운 town의 사용자 목록에 추가
        if (town != null && !town.getUsers().contains(this)) {
            town.getUsers().add(this);
        }
    }

}
