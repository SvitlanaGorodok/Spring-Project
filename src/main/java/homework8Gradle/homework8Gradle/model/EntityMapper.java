package homework8Gradle.homework8Gradle.model;

import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

//@Mapper(componentModel = "Spring", imports = {LocalDate.class})
public interface EntityMapper {

//    @Mapping(target = "passwordConfirm", ignore = true)
    UserDto userToDto(User user);
    User userToDAO(UserDto userDTO);
}
