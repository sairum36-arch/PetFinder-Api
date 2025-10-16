package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.NotificationEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
   @Query("SELECT n FROM NotificationEntity n " + "WHERE n.userEntity = :user " + "ORDER BY n.createdAt DESC")
    List<NotificationEntity> findAllByUserSortedByDate(@Param("user")UserEntity userEntity);

    @Query("SELECT COUNT(n) FROM NotificationEntity n WHERE n.userEntity = :userEntity AND n.isRead = false")
    long countUnreadNotificationsForUser(@Param("userEntity") UserEntity userEntity);
}
