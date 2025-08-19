package mapper;

import dto.mainProfileDTOS.GeoFenceDTO;
import entity.GeoFence;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeoFenceMapper {
    GeoFenceDTO toGeoFenceDTO(GeoFence geoFence);
    GeoFence toGeoFenceEntity(GeoFenceDTO geoFence);
    List<GeoFenceDTO> toGeoFenceDTOList(List<GeoFence> geoFence);
}
