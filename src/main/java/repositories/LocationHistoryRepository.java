package repositories;

import entity.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationHistoryRepository  extends JpaRepository<LocationHistory, Long> {
}
