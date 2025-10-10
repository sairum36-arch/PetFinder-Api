package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "locations_history")
public class LocationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_history_id_generator")
    @SequenceGenerator(name = "locations_history_id_generator", sequenceName = "locations_history_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="collar_id")
    private CollarEntity collarEntity;

    @Column(columnDefinition =  "geography")
    private Point location;
    private LocalDateTime timestamp;

}
