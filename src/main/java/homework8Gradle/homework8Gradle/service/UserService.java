package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapper;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<UserDto> {
    private final UserRepository repository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final EntityMapper mapper;

    @Override
    public UserDto save(UserDto user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        if (user.getPassword() == null) {
            user.setPassword(encoder.encode(user.getEmail()));
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

    public void register(UserDto user) {
        user.setRoleId(roleService.findByName("ROLE_USER"));
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }

    public List<String> findAllEmails() {
        return repository.findAllEmails();
    }

    public UserDto findByEmail(String email){
        User userByEmail = repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEntityFoundException("User with email " + email + "not found!"));
        return mapper.userToDto(userByEmail);
    }

    public void changePassword(UserDto user){
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }

}
