package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "incident_responses")
public class IncidentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "incident_id")
    private Incident incident;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helper_user_id")
    private User helperUser;
    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}
