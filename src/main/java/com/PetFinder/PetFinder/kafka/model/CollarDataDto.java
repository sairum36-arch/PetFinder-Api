package com.PetFinder.PetFinder.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollarDataDto {
    private String deviceId;
    private double latitude;
    private double longitude;
    private byte batteryLevel;
}
