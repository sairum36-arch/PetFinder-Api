package repositories;

import entity.GeoFence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoFenceRepository extends JpaRepository<GeoFence, Long> {
}
