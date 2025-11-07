package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Collar;
import com.PetFinder.PetFinder.dto.websocket.LocationUpdateMessage;
import com.PetFinder.PetFinder.entity.CollarEntity;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.kafka.model.CollarDataDto;
import com.PetFinder.PetFinder.repositories.CollarRepository;
import org.instancio.exception.InstancioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.instancio.Instancio;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.instancio.junit.InstancioExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class CollarDataProcessServiceTest {
    @Mock
    private CollarRepository collarRepository;

    @Mock
    private  NotificationService notificationService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CollarDataProcessService collarDataProcessService;

    @Test
    public void processNewCollarDataAndUpdateLocation(){
        CollarDataDto testDto = Instancio.of(CollarDataDto.class).set(field("deviceId"), "TEST-001")
                .set(field("batteryLevel"), (byte) 85)
                .create();

        UserEntity owner = new UserEntity();
        owner.setId(1L);
        PetEntity pet = new PetEntity();
        pet.setUserEntity(owner);
        CollarEntity collar = new CollarEntity();
        collar.setPetEntity(pet);

        when(collarRepository.findByDeviceId("TEST-001")).thenReturn(Optional.of(collar));
        collarDataProcessService.processNewCollarData(testDto);
        assertEquals(testDto.getBatteryLevel(), collar.getBatteryLevel());
        assertEquals(testDto.getLongitude(), collar.getLastLocation().getX());
        assertEquals(testDto.getLatitude(), collar.getLastLocation().getY());
        verify(notificationService, never()).createNotification(any(), any(), any());
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/user-locations/1"), any(LocationUpdateMessage.class));
    }

    @Test
    public void createLowBatteryNotificationWhenBatteryDropsThreshold(){
        CollarDataDto lowBatteryDto = Instancio.of(CollarDataDto.class).set(field("deviceId"), "TEST-002").set(field("batteryLevel"),(byte) 15).create();
        UserEntity owner = Instancio.create(UserEntity.class);
        PetEntity pet  = new PetEntity();
        pet.setUserEntity(owner);
        pet.setName("Рекс");
        CollarEntity collarInDb = new CollarEntity();
        collarInDb.setPetEntity(pet);
        collarInDb.setBatteryLevel((byte) 25);
        when(collarRepository.findByDeviceId("TEST-002")).thenReturn(Optional.of(collarInDb));
        collarDataProcessService.processNewCollarData(lowBatteryDto);
        verify(notificationService, times(1)).createNotification(any(),any(),any());



    }
}