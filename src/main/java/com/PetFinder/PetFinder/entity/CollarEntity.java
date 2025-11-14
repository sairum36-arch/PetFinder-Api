package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.locationtech.jts.geom.Point;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "collars")
public class CollarEntity extends  AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collars_id_generator")
    @SequenceGenerator(name = "collars_id_generator", sequenceName = "collars_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name="pet_id")
    private PetEntity petEntity;

    @Column(name = "device_id")
    private String deviceId;

    @Enumerated(EnumType.STRING)
    private CollarStatus status;

    @Column(name="battery_level")
    private Byte batteryLevel;

    @OneToMany(mappedBy = "collarEntity")
    private List<LocationHistoryEntity> locationHistoryEntity;

    @Column(name = "last_location", columnDefinition = "geography(Point, 4326)")
    private Point lastLocation;


}
