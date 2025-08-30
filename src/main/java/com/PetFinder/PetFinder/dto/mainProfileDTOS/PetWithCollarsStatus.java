package com.PetFinder.PetFinder.dto.mainProfileDTOS;

import com.PetFinder.PetFinder.entity.CollarStatus;
import lombok.Data;

@Data
public class PetWithCollarsStatus {
    private Long petId;
    private String petName;
    private String petMainPhotoUrl;
    private Long collarId;
    private CollarStatus collarStatus;
    private Byte batteryLevel;

}
