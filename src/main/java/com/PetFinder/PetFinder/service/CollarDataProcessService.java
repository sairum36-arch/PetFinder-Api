package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.websocket.CollarStatusUpdateMessage;
import com.PetFinder.PetFinder.dto.websocket.LocationUpdateDto;
import com.PetFinder.PetFinder.dto.websocket.LocationUpdateMessage;
import com.PetFinder.PetFinder.entity.CollarEntity;
import com.PetFinder.PetFinder.entity.GeoFenceEntity;
import com.PetFinder.PetFinder.entity.NotificationType;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.kafka.model.CollarDataDto;
import com.PetFinder.PetFinder.repositories.CollarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollarDataProcessService {
    private final CollarRepository collarRepository;
    private final NotificationService notificationService;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final SimpMessagingTemplate messagingTemplate;
    private static final byte BATTERY_THRESHOLD = 20;


    @Transactional
    public void processNewCollarData(CollarDataDto data){
        collarRepository.findByDeviceId((data.getDeviceId())).ifPresent(collar -> {
            processBatteryUpdate(collar, data.getBatteryLevel());
            Point newLocation = geometryFactory.createPoint(new Coordinate(data.getLongitude(), data.getLatitude()));
            newLocation.setSRID(4326);
            processLocationUpdate(collar, newLocation);
        });
    }

    private  void processBatteryUpdate(CollarEntity collar, byte newBatteryLevel){
         Byte oldBatteryLevel = collar.getBatteryLevel();
         if(newBatteryLevel < BATTERY_THRESHOLD && (oldBatteryLevel == null || oldBatteryLevel >= BATTERY_THRESHOLD)){
             UserEntity owner = collar.getPetEntity().getUserEntity();
             String petName = collar.getPetEntity().getName();
             String message = String.format("Низкий заряд ошейника у питомца '%s'! Осталось %d%%.", petName, newBatteryLevel);
             notificationService.createNotification(owner, NotificationType.LOW_BATTERY, message);
         }
         collar.setBatteryLevel(newBatteryLevel);
    }

    private void processLocationUpdate(CollarEntity collar, Point newLocation) {
        List<GeoFenceEntity> geofences = collar.getPetEntity().getGeoFenceEntities();
        Point lastLocation = collar.getLastLocation();
        if (geofences != null && !geofences.isEmpty() && lastLocation != null) {
            for (GeoFenceEntity geofence : geofences) {
                boolean isInside = geofence.getArea().contains(newLocation);
                boolean wasInside = geofence.getArea().contains(lastLocation);
                if (wasInside && !isInside) {
                    UserEntity owner = collar.getPetEntity().getUserEntity();
                    String petName = collar.getPetEntity().getName();
                    String message = String.format("Внимание! Питомец '%s' покинул геозону '%s'.", petName, geofence.getName());
                    notificationService.createNotification(owner, NotificationType.EXIT, message);
                }
            }
        }
        collar.setLastLocation(newLocation);
        Long petId = collar.getPetEntity().getId();
        Long userId = collar.getPetEntity().getUserEntity().getId();
        String destination = "/topic/user-locations/" + userId;
        LocationUpdateMessage payload = new LocationUpdateMessage(petId, newLocation.getY(), newLocation.getX());
        messagingTemplate.convertAndSend(destination, payload);
    }

    public void sendCollarStatusUpdate(CollarEntity collar) {
        if (collar == null || collar.getPetEntity() == null) {
            return;
        }
        Long userId = collar.getPetEntity().getUserEntity().getId();
        String destination = "/topic/user-collars/" + userId;
        CollarStatusUpdateMessage payload = new CollarStatusUpdateMessage(
                collar.getPetEntity().getId(),
                collar.getStatus(),
                collar.getBatteryLevel()
        );
        messagingTemplate.convertAndSend(destination, payload);
    }
}
