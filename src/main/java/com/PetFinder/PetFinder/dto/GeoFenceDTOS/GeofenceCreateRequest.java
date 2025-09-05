package com.PetFinder.PetFinder.dto.GeoFenceDTOS;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;

@Data
public class GeofenceCreateRequest {
    private String name;
    private Long petId;
    private List<CoordinateDto> points;
}
