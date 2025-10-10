package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import com.PetFinder.PetFinder.dto.locationHistory.LocationPoint;
import com.PetFinder.PetFinder.entity.LocationHistoryEntity;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationHistoryMapper {
    @Mapping(source = "location", target = "location")
    LocationPoint toDto(LocationHistoryEntity entity);

    List<LocationPoint> toDtoList(List<LocationHistoryEntity> entities);

    default CoordinateDto pointToCoordinateDto(Point point) {
        if (point == null) {
            return null;
        }
        return new CoordinateDto(point.getX(), point.getY());
    }
}
