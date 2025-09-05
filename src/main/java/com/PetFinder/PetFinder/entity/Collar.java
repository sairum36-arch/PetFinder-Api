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
public class Collar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="pet_id")
    private Pet pet;
    @Column(name = "device_id")
    private String deviceId;
    @Enumerated(EnumType.STRING)
    private CollarStatus status;
    @Column(name="battery_level")
    private Byte batteryLevel;
    @OneToMany(mappedBy = "collar")
    private List<LocationHistory> locationHistory;
    @Column(name = "last_location", columnDefinition = "geography(Point, 4326)")
    private Point lastLocation;


}
