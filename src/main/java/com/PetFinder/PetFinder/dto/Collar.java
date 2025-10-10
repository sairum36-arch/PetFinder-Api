package com.PetFinder.PetFinder.dto;

import com.PetFinder.PetFinder.entity.CollarStatus;
import lombok.Data;

@Data
public class Collar {
    private Long id;
    private String deviceId;
    private CollarStatus status;
    private Byte batteryLevel;
}
