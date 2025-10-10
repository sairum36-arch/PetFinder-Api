package com.PetFinder.PetFinder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_id_generator")
    @SequenceGenerator(name = "notifications_id_generator", sequenceName = "notifications_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name="created_at")
    private LocalDateTime createdAt;

}
