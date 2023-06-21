package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RoleServiceTest {
    @Mock
    RoleRepository repository;

    RoleService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new RoleService(repository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setId(id);
        role.setName("role");

        when(repository.save(any(Role.class))).thenAnswer(i -> i.getArguments()[0]);

        Role saved = service.save(role);

        assertEquals(role.getId(), saved.getId());
        assertEquals(role.getName(), saved.getName());

    }

    @Test
    void saveWithNullId() {
        Role toSave = new Role();
        assertNull(toSave.getId());

        when(repository.save(any(Role.class))).thenAnswer(i -> i.getArguments()[0]);

        Role saved = service.save(toSave);

        assertNotNull(saved.getId());
    }

    @Test
    void findAll() {
        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setId(id);
        role.setName("role");

        when(repository.findAll()).thenReturn(List.of(role));

        List<Role> roles = service.findAll();

        assertEquals(roles.get(0).getId(), role.getId());
        assertEquals(roles.get(0).getName(), role.getName());

    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setId(id);
        role.setName("role");

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(role));

        Role roleById = service.findById(id);

        assertEquals(id, roleById.getId());
    }

    @Test
    void findByIdNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findById(id));

        assertEquals("Role with id " + id + "not found!", exception.getMessage());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        service.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }

    @Test
    void findByName() {
        UUID id = UUID.randomUUID();
        Role role = new Role();
        role.setId(id);
        role.setName("role");

        when(repository.findByName(any(String.class))).thenReturn(Optional.of(role));

        UUID roleId = service.findByName("role");

        assertEquals(role.getId(), roleId);

    }

    @Test
    void findByNameNoSuchEntityFoundException() {
        String name = "role";

        when(repository.findByName(any(String.class))).thenReturn(Optional.empty());

        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findByName(name));

        assertEquals("Role with name " + name + "not found!", exception.getMessage());

    }
}