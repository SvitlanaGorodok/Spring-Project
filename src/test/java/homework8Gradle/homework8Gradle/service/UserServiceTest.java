package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapperImpl;
import homework8Gradle.homework8Gradle.repository.ProductRepository;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    RoleService roleService;

    UserService service;
    BCryptPasswordEncoder encoder;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        encoder = new BCryptPasswordEncoder(12);
        service = new UserService(repository, roleService, encoder, new EntityMapperImpl());
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email");
        userDto.setPassword("password");
        userDto.setRoleId(roleId);

        when(repository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserDto saved = service.save(userDto);

        assertEquals(userDto.getId(), saved.getId());
        assertEquals(userDto.getFirstName(), saved.getFirstName());
        assertEquals(userDto.getLastName(), saved.getLastName());
        assertEquals(userDto.getEmail(), saved.getEmail());
        assertEquals(userDto.getPassword(), saved.getPassword());
        assertEquals(userDto.getRoleId(), saved.getRoleId());
    }

    @Test
    void saveWithNullIdAndPassword() {
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        UserDto toSave = new UserDto();
        toSave.setEmail("email");
        assertNull(toSave.getId());
        assertNull(toSave.getPassword());

        UserDto saved = service.save(toSave);
        assertNotNull(saved.getId());
        assertNotNull(saved.getPassword());
        assertThat(encoder.matches(toSave.getEmail(), saved.getPassword())).isTrue();
    }

    @Test
    void findAll() {
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email");
        userDto.setPassword("password");
        userDto.setRoleId(roleId);

        User user = new User();
        Role role = new Role();
        role.setId(roleId);
        user.setId(id);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(role);

        when(repository.findAll()).thenReturn(List.of(user));

        List<UserDto> userDtos = service.findAll();

        assertEquals(userDtos.get(0).getId(), userDto.getId());
        assertEquals(userDtos.get(0).getFirstName(), userDto.getFirstName());
        assertEquals(userDtos.get(0).getLastName(), userDto.getLastName());
        assertEquals(userDtos.get(0).getEmail(), userDto.getEmail());
        assertEquals(userDtos.get(0).getPassword(), userDto.getPassword());
        assertEquals(userDtos.get(0).getRoleId(), userDto.getRoleId());
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        UserDto userDto = service.findById(id);

        assertEquals(id, userDto.getId());
    }

    @Test
    void findByIdNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findById(id));
        assertEquals("User with id " + id + "not found!", exception.getMessage());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        service.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }

    @Test
    void register() {
        UUID roleId = UUID.randomUUID();

        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email");
        String rawPassword = "password";
        userDto.setPassword(rawPassword);

        when(repository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(roleService.findByName("ROLE_USER")).thenReturn(roleId);

        service.register(userDto);

        assertEquals(userDto.getRoleId(), roleId);
        assertThat(encoder.matches(rawPassword, userDto.getPassword())).isTrue();
    }

    @Test
    void findAllEmails() {
        List<String> emails = List.of("email1", "email2");
        when(service.findAllEmails()).thenReturn(emails);
        assertEquals(emails, service.findAllEmails());
    }

    @Test
    void findByEmail() {
        String email = "email";
        User user = new User();
        user.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userDto = service.findByEmail(email);

        assertEquals(email, userDto.getEmail());
    }

    @Test
    void findByEmailNoSuchEntityFoundException() {
        String email = "email";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findByEmail(email));
        assertEquals("User with email " + email + "not found!", exception.getMessage());
    }

    @Test
    void changePassword() {
        UserDto userDto = new UserDto();
        String rawPassword = "password";
        userDto.setPassword(rawPassword);

        when(repository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        service.register(userDto);

        assertThat(encoder.matches(rawPassword, userDto.getPassword())).isTrue();
    }

    @Test
    void findByParameters() {
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email");
        userDto.setRoleId(roleId);

        User user = new User();
        Role role = new Role();
        role.setId(roleId);
        user.setId(id);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(role);

        when(repository.findByParameters(anyString(), anyString(), anyString(), any(UUID.class)))
                .thenReturn(List.of(user));

        UserDto findUser = service.findByParameters(userDto).get(0);

        assertEquals(findUser.getFirstName(), userDto.getFirstName());
        assertEquals(findUser.getLastName(), userDto.getLastName());
        assertEquals(findUser.getEmail(), userDto.getEmail());
        assertEquals(findUser.getRoleId(), userDto.getRoleId());
    }
}