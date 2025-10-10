package com.PetFinder.PetFinder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Entity
@Table(name = "incidents")
public class IncidentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incidents_id_generator")
    @SequenceGenerator(name = "incidents_id_generator", sequenceName = "incidents_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by_user_id")
    private UserEntity resolvedBy;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;
    private BigDecimal reward;

    @Column(name="last_known_location", columnDefinition = "geography")
    private Point lastKnownLocation;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "incidentEntity")
    private List<IncidentMessageEntity> messages;

    @OneToMany(mappedBy = "incidentEntity")
    private List<IncidentParticipationEntity> responses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity petEntity;

    @Column(name = "owners_notes")
    private String ownersNotes;

}
