package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ManufacturerService implements CrudService<Manufacturer>{
    private final ManufacturerRepository repository;

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        if (manufacturer.getId() == null || manufacturer.getId().isEmpty()){
            manufacturer.setId(UUID.randomUUID().toString());
        }
        return repository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Manufacturer findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
