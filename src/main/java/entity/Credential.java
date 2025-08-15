package entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "credentials")
public class Credential {
    @Id
    @Column(name="user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name= "user_id")
    private User user;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

}
