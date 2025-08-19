package entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
