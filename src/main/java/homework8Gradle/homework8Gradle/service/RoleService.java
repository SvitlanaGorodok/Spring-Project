package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService implements CrudService<Role>{
    private final RoleRepository repository;
    @Override
    public Role save(Role object) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findById(UUID id) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    public UUID findByName(String name){
        Role role = repository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityFoundException("Role with name " + name + "not found!"));
        return role.getId();
    }
}
