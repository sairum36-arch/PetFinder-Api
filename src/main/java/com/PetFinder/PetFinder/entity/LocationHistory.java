package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "locations_history")
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="collar_id")
    private Collar collar;
    @Column(columnDefinition =  "geography")
    private Point location;
    private LocalDateTime timestamp;

}
