package com.PetFinder.PetFinder.kafka.service;

import com.PetFinder.PetFinder.service.CollarDataProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.PetFinder.PetFinder.kafka.model.CollarDataDto;
@Service
@RequiredArgsConstructor
@Slf4j
public class CollarDataConsumer {
    private final CollarDataProcessService dataProcessService;

    @KafkaListener(topics = "collar-data-updates", groupId = "petfinder-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(CollarDataDto data) {
        log.info("Получены данные с Kafka: {}", data);

        if (data.getBatteryLevel() == 66) {
            log.error("симуляция ошибки обработки для deviceId: {}", data.getDeviceId());
            throw new RuntimeException("симуляция сбоя");
        }
        dataProcessService.processNewCollarData(data);
    }
}
