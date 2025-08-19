package repositories;

import entity.Collar;
import entity.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LocationHistoryRepository  extends JpaRepository<LocationHistory, Long> {
    List<LocationHistory> findByCollarAndTimestampBetweenOrderByTimestampAsc(Collar collar, LocalDateTime start, LocalDateTime end);

}
