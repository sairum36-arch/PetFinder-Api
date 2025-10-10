package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "breeds")
public class BreedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "breeds_id_generator")
    @SequenceGenerator(name = "breeds_id_generator", sequenceName = "breeds_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;

}
