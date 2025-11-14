package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.repositories.LocationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SheduledTasksService {

    private final LocationHistoryRepository locationHistoryRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanOldLocationHistory(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        log.info("удаление записей которые старше ", sevenDaysAgo);
        int deletedCount = locationHistoryRepository.deleteByTimestampBefore(sevenDaysAgo);
        log.info("удалено строчек: ", deletedCount);
    }

    //чтоб протестить
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void cleanOldLocationHistoryTest(){
        cleanOldLocationHistory();
    }
}
