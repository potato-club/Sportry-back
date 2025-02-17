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
public class SportTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_type_id")
    private Long id;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<SportEntity> sports;

    private String name;

}
