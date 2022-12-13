package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements CrudService<User>{
    private final UserRepository repository;

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
}
