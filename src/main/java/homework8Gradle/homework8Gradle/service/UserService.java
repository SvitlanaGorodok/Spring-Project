package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.UserAlreadyExistException;
import homework8Gradle.homework8Gradle.model.UserRole;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<User>{
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isEmpty()){
            user.setId(UUID.randomUUID().toString());
        }
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
    public void register(User user){
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(String.format("User with specified email already exist %s", user.getEmail()));
        }
        user.setRoles(Set.of(UserRole.ROLE_USER));
        user.setPassword(encoder.encode(user.getPassword()));
        save(user);
    }
}
