package homework8Gradle.homework8Gradle.model.dto;

import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    String id;
    String email;
    String password;
    String firstName;
    String lastName;
    Role role;

    public static User toUser(UserDto userDto){
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(UUID.fromString(userDto.getId()));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        return user;
    }

    public static UserDto fromUser(User user){
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId().toString());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(null);
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
