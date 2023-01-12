package homework8Gradle.homework8Gradle.model.dto;

import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    String id;
    String email;
    String password;
    String firstName;
    String lastName;
    Set<Role> roles;

    public static User toUser(UserDto userDto){
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public static UserDto fromUser(User user){
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(null);
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
