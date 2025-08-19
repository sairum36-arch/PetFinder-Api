package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "incident_messages")
public class IncidentMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incidents;
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User sender;
    @Column(name = "message_text")
    private String messageText;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
