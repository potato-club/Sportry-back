package com.gamza.sportry.entity;


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
public class CityEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 시군구 이름

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region; // 해당 시군구가 속한 시도

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<TownEntity> towns; // 읍면동 리스트

}
