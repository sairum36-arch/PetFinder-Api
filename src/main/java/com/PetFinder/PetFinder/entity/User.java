package com.PetFinder.PetFinder.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo необязательно
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Credential credential;

    @OneToMany(mappedBy = "user")
    private List<Pet> pets;

    @OneToMany(mappedBy = "helperUser", cascade =  CascadeType.ALL)
    private List<IncidentResponse> helpedResponses;
}
