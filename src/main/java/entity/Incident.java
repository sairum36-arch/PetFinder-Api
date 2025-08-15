package entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Point;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by_user_id")
    private User resolvedBy;
    @Enumerated(EnumType.STRING)
    private IncidentStatus status;
    private BigDecimal reward;
    @Column(name="last_known_location", columnDefinition = "geography")
    private Point lastKnownLocation;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @OneToMany(mappedBy = "incident")
    private List<IncidentMessage> messages;
    @OneToMany(mappedBy = "incident")
    private List<IncidentResponse> responses;


}
