package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pet_media")
public class PetMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pet_id", nullable = false)
    private Pet pet;
    @Column(nullable = false)
    private String url;
    @Column(name="display_order")
    private Integer displayOrder;
    private boolean isMainPhoto;
}
