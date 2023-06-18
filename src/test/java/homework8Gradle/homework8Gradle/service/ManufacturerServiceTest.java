package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapper;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapperImpl;
import homework8Gradle.homework8Gradle.repository.ManufacturerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ManufacturerServiceTest {

    @Mock
    ManufacturerRepository repository;

    ManufacturerService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new ManufacturerService(repository, new EntityMapperImpl());
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(id);
        manufacturerDto.setName("name");

        when(repository.save(any(Manufacturer.class))).thenAnswer(i -> i.getArguments()[0]);

        ManufacturerDto saved = service.save(manufacturerDto);

        assertEquals(manufacturerDto.getId(), saved.getId());
        assertEquals(manufacturerDto.getName(), saved.getName());
    }

    @Test
    void saveWithNullId() {
        when(repository.save(any(Manufacturer.class))).thenAnswer(i -> i.getArguments()[0]);
        ManufacturerDto toSave = new ManufacturerDto();
        assertNull(toSave.getId());

        ManufacturerDto saved = service.save(toSave);
        assertNotNull(saved.getId());
    }

    @Test
    void findAll() {
        UUID id = UUID.randomUUID();

        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(id);
        manufacturerDto.setName("name");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName("name");

        when(repository.findAll()).thenReturn(List.of(manufacturer));

        List<ManufacturerDto> manufacturerDtos = service.findAll();

        assertEquals(manufacturerDtos.get(0).getId(), manufacturerDto.getId());
        assertEquals(manufacturerDtos.get(0).getName(), manufacturerDto.getName());
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName("name");

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(manufacturer));

        ManufacturerDto manufacturerDto = service.findById(id);

        assertEquals(id, manufacturerDto.getId());
    }

    @Test
    void findByIdNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findById(id));
        assertEquals("Manufacturer with id " + id + "not found!", exception.getMessage());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        service.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }

    @Test
    void findAllNames() {
        List<String> names = List.of("name1", "name2");
        when(repository.findAllNames()).thenReturn(names);
        assertEquals(names, service.findAllNames());
    }
}