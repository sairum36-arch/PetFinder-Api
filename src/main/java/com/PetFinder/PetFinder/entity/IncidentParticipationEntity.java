package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "incident_participations")

public class IncidentParticipationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_participations_id_generator")
    @SequenceGenerator(name = "incident_participations_id_generator", sequenceName = "incident_participations_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "incident_id")
    private IncidentEntity incidentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helper_user_id")

    private UserEntity helperUserEntity;
    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}
