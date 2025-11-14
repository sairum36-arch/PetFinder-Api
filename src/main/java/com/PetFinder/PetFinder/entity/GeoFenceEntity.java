package com.PetFinder.PetFinder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import org.locationtech.jts.geom.Polygon;


@Getter
@Setter
@Entity
@Table(name = "geofences")
public class GeoFenceEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geofences_id_generator")
    @SequenceGenerator(name = "geofences_id_generator", sequenceName = "geofences_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="pet_id")
    private PetEntity petEntity;
    private String name;

    @Column(columnDefinition = "geography")
    private Polygon area;

}
