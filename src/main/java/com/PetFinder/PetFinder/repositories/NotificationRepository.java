package com.PetFinder.PetFinder.repositories;

import com.PetFinder.PetFinder.entity.Notification;
import com.PetFinder.PetFinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
