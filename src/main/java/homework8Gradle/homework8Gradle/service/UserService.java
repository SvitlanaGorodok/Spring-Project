package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.EntityMapper;
import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<UserDto>{
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final EntityMapper mapper;

    @Override
    public UserDto save(UserDto user) {
        if (user.getId() == null){
            user.setId(UUID.randomUUID());
        }
        User saved = repository.save(mapper.userToDao(user));
        return mapper.userToDto(saved);
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(UUID id) {
        User userById = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityFoundException("User with id " + id + "not found!"));
        return mapper.userToDto(userById);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
    public void register(UserDto user){
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(
                    String.format("User with specified email %s already exist", user.getEmail()));
        }
        //Set user role = "ROLE_USER"
        user.setRoleId(UUID.fromString("495c0735-2411-46f2-8d3c-0fd91a636088"));

        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }
}
