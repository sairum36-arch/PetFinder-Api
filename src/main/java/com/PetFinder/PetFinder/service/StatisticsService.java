package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.StatisticsSumDto;
import com.PetFinder.PetFinder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public StatisticsSumDto getSumm(){
        Map<String, Object> results = userRepository.getStatisticsSumm();
        long totalUsers = ((Number) results.get("totalusers")).longValue();
        long totalPets = ((Number) results.get("totalpets")).longValue();
        long activeIncidents = ((Number) results.get("activeincidents")).longValue();

        return new StatisticsSumDto(totalUsers, totalPets, activeIncidents);
    }
}
