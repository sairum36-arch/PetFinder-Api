package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import com.PetFinder.PetFinder.dto.GeoFence.GeoFenceResponse;
import com.PetFinder.PetFinder.dto.mainProfile.GeoFence;
import com.PetFinder.PetFinder.entity.GeoFenceEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GeoFenceMapper {
    @Mapping(source = "petEntity.id", target = "petId")
    @Mapping(source = "area", target = "points")
    GeoFenceResponse toDTO(GeoFenceEntity geoFenceEntity);

    List<GeoFenceResponse> toDTOList(List<GeoFenceEntity> geoFenceEntity);
    default List<CoordinateDto> polygonToCoordinateDtoList(Polygon polygon){
        if(polygon == null){
            return null;
        }
        return Arrays.stream(polygon.getCoordinates())
                .map(jtsCoordinate -> {
                    CoordinateDto dto = new CoordinateDto();
                    dto.setLongitude(jtsCoordinate.getX());
                    dto.setLatitude(jtsCoordinate.getY());
                    return dto;
                }).collect(Collectors.toList());
    }

    default Polygon coordinateDtoListToPolygon(List<CoordinateDto> points){
        if(points == null || points.size() < 4){
            return null;
        }
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordinates = points.stream().map(p -> new Coordinate(p.getLongitude(), p.getLatitude())).toArray(Coordinate[]::new);
        return geometryFactory.createPolygon(coordinates);
    }
}
