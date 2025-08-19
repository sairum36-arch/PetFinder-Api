package entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.locationtech.jts.geom.Polygon;


@Getter
@Setter
@Entity
@Table(name = "geo_fences")
public class GeoFence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="pet_id")
    private Pet pet;
    private String name;
    @Column(columnDefinition = "geography")
    private Polygon area;

}
