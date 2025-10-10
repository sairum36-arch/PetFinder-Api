package com.PetFinder.PetFinder.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_generator")
    @SequenceGenerator(name = "users_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String phoneNumber;

    @OneToOne(mappedBy = "userEntity" , cascade = CascadeType.ALL)
    private CredentialEntity credentialEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<PetEntity> petEntities;

    @OneToMany(mappedBy = "helperUserEntity", cascade =  CascadeType.ALL)
    private List<IncidentParticipationEntity> participations;

    @Column(name="avatar_key")
    private String avatarKey;

}
