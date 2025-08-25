package mapper;


import dto.mainProfileDTOS.GeoFenceDTO;
import dto.mainProfileDTOS.PetWithCollarsStatus;
import dto.mainProfileDTOS.UserProfileResponse;
import entity.GeoFence;
import entity.Pet;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, GeoFenceMapper.class})
public interface UserMapper {
    @Mapping(source = "pets", target = "pets")
    @Mapping(source = "pets", target = "geofences")
    @Mapping(target = "activeCollarsCount", expression = "java(user.getPets().size())")
    UserProfileResponse toUserProfileResponse(User user);
    default List<GeoFence> collectAllGeofences(List<Pet> pets){
        List<GeoFence> allGeofences = new ArrayList<>();
        for(Pet pet: pets){
            allGeofences.addAll(pet.getGeoFences());
        }
        return allGeofences;
    }

    User toEntity(UserProfileResponse userProfileResponse);
}
