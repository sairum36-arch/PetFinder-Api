package mapper;

import dto.mainProfileDTOS.GeoFenceDTO;
import entity.GeoFence;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeoFenceMapper {
    GeoFenceDTO toDTO(GeoFence geoFence);
    GeoFence toEntity(GeoFenceDTO geoFence);
    List<GeoFenceDTO> toDTOList(List<GeoFence> geoFence);
}
