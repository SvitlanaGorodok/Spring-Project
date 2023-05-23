package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapper;
import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManufacturerService implements CrudService<ManufacturerDto>{
    private final ManufacturerRepository repository;
    private final EntityMapper mapper;

    @Override
    public ManufacturerDto save(ManufacturerDto manufacturer) {
        if (manufacturer.getId() == null){
            manufacturer.setId(UUID.randomUUID());
        }
        Manufacturer saved = repository.save(mapper.manufacturerToDao(manufacturer));
        return mapper.manufacturerToDto(saved);
    }

    @Override
    public List<ManufacturerDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::manufacturerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManufacturerDto findById(UUID id) {
        Manufacturer manufacturerById = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityFoundException("Manufacturer with id " + id + "not found!"));
        return mapper.manufacturerToDto(manufacturerById);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<String> findAllNames(){
        return repository.findAllNames();
    }
}
