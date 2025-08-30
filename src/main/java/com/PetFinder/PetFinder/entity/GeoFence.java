package com.PetFinder.PetFinder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import org.locationtech.jts.geom.Polygon;


@Getter
@Setter
@Entity
@Table(name = "geofences")
public class GeoFence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="pet_id")
    private Pet pet;
    private String name;
    @Column(columnDefinition = "geography")
    private Polygon area;

}
