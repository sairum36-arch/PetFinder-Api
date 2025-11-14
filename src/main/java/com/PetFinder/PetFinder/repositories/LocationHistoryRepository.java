package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.CollarEntity;
import com.PetFinder.PetFinder.entity.LocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface LocationHistoryRepository  extends JpaRepository<LocationHistoryEntity, Long> {
    @Query("SELECT lh FROM LocationHistoryEntity lh " + "WHERE lh.collarEntity = :collar " + "AND lh.timestamp BETWEEN :start AND :end " + "ORDER BY lh.timestamp ASC")
    List<LocationHistoryEntity> findHistoryForPeriod(@Param("collar") CollarEntity collarEntity,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Modifying
    @Transactional
    @Query("DELETE FROM LocationHistoryEntity lh WHERE lh.timestamp < :cutoffTimestamp")
    int deleteByTimestampBefore(@Param("cutoffTimestamp") LocalDateTime cutoffTimestamp);
}
