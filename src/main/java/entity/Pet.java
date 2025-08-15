package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    @ManyToOne
    @JoinColumn(name="breed_id")
    private Breed breed;
    private String description;
    private Long weight;
    private Long age;
    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    private Collar collar;
    @OneToMany(mappedBy = "pet")
    private List<GeoFence> geoFences;

}
