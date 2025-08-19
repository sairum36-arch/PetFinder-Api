package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class UserRegistrationRequest {
    private String email;
    private String password;
    private String fullname;
    private String phoneNumber;

}
