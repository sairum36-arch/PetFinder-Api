package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "pets")
public class PetEntity extends  AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pets_id_generator")
    @SequenceGenerator(name = "pets_id_generator", sequenceName = "pets_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String name;

    @ManyToOne
    @JoinColumn(name="breed_id")
    private BreedEntity breedEntity;

    private String description;

    private Long weight;

    private Long age;

    @OneToOne(mappedBy = "petEntity", cascade = CascadeType.ALL)
    private CollarEntity collarEntity;

    @OneToMany(mappedBy = "petEntity")
    private List<GeoFenceEntity> geoFenceEntities;

    @OneToMany(mappedBy = "petEntity", cascade = CascadeType.ALL)
    private List<PetMediaEntity> mediaFiles;
}
