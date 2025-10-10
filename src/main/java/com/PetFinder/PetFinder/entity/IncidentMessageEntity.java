package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "incident_messages")
public class IncidentMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_messages_id_generator")
    @SequenceGenerator(name = "incident_messages_id_generator", sequenceName = "incident_messages_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private IncidentEntity incidentEntity;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private UserEntity sender;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
