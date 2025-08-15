package repositories;

import entity.IncidentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentMessageRepository extends JpaRepository<IncidentMessage, Long> {
}
