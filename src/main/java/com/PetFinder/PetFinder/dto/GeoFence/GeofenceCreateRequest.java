package com.PetFinder.PetFinder.dto.GeoFence;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import lombok.Data;

import java.util.List;

@Data
public class GeofenceCreateRequest {
    private String name;
    private Long petId;
    private List<CoordinateDto> points;

}
