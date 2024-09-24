package com.gamza.sportry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 읍면동 이름

    @ManyToMany(mappedBy = "towns")
    private List<UserEntity> users; // 다대다 관계로 변경

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city; // 해당 읍면동이 속한 시군구
}