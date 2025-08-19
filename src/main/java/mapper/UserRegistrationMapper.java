package mapper;


import dto.UserRegistrationRequest;
import entity.Credential;
import entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    User toUser(UserRegistrationRequest request);

    Credential toCredential(UserRegistrationRequest request);

}
