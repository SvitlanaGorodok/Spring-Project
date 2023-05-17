package homework8Gradle.homework8Gradle.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    UUID roleId;
    String userDetails;
}
