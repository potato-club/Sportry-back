package com.gamza.sportry.entity;

import com.gamza.sportry.core.entity.BaseEntity;
import com.gamza.sportry.entity.custom.Gender;
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
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CareerEntity> careers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClassEntity> classes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentLikeEntity> commentLikes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_town", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "town_id")
    )
    private List<TownEntity> towns = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private FileEntity profileImg;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birth;


    public void updateRefreshToken(String RT) {
        this.refreshToken = RT;
    }

    public void changeCity(CityEntity newCity) {
        if (this.city != null) {
            this.city.getUsers().remove(this);
        }

        this.city = newCity;
        if (newCity != null) {
            newCity.getUsers().add(this);
        }
    }
}
