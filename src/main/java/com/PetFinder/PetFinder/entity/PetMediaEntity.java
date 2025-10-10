package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pet_media")
public class PetMediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_media_id_generator")
    @SequenceGenerator(name = "pet_media_id_generator", sequenceName = "pet_media_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pet_id", nullable = false)
    private PetEntity petEntity;

    @Column(nullable = false)
    private String url;

    @Column(name="display_order")
    private Integer displayOrder;

    private boolean isMainPhoto;
}
