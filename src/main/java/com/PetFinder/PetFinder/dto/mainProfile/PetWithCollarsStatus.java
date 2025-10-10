package com.PetFinder.PetFinder.dto.mainProfile;

import com.PetFinder.PetFinder.dto.CoordinateDto;
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
    private CoordinateDto lastKnownLocation;
}
