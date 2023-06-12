package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dto.FindProductParam;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import homework8Gradle.homework8Gradle.security.UserPrincipal;
import homework8Gradle.homework8Gradle.service.RoleService;
import homework8Gradle.homework8Gradle.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    UserService service;

    @Mock
    RoleService roleService;

    @Mock
    UserPrincipal userPrincipal;

    @InjectMocks
    UserController controller;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void showAll() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto());
        users.add(new UserDto());

        when(service.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/findall"))
                .andExpect(model().attribute("users", hasSize(2)));
    }

    @Test
    void saveForm() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());

        List<String> emails = new ArrayList<>();
        emails.add("email1");
        emails.add("email2");

        when(roleService.findAll()).thenReturn(roles);
        when(service.findAllEmails()).thenReturn(emails);

        mockMvc.perform(get("/users/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/save"))
                .andExpect(model().attribute("roles", hasSize(2)))
                .andExpect(model().attribute("emails", hasSize(2)));

    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("/users/save")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email@gmail.com")
                        .param("roleId", UUID.randomUUID().toString())
                        .flashAttr("userDto", new UserDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void updateForm() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());

        List<String> emails = new ArrayList<>();
        emails.add("email1");
        emails.add("email2");

        UUID id = UUID.randomUUID();

        UserDto user = new UserDto();
        user.setId(id);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@gmail.com");
        user.setRoleId(UUID.randomUUID());
        user.setPassword("password");
        user.setUserDetails("userDetails");

        when(service.findById(id)).thenReturn(user);
        when(roleService.findAll()).thenReturn(roles);
        when(service.findAllEmails()).thenReturn(emails);

        mockMvc.perform(get("/users/update/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/update"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("roles", hasSize(2)))
                .andExpect(model().attribute("emails", hasSize(2)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/delete/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void changePasswordForm() throws Exception {
        UserDto user = new UserDto();
        user.setId(UUID.randomUUID());
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@gmail.com");
        user.setRoleId(UUID.randomUUID());
        user.setPassword("password");
        user.setUserDetails("userDetails");

        when(service.findByEmail(any(String.class))).thenReturn(user);
        when(userPrincipal.getUsername()).thenReturn("email@gmail.com");

        mockMvc.perform(get("/users/changepassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/changepassword"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    void changePassword() {
    }

    @Test
    void findForm() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());

        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/users/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/find"))
                .andExpect(model().attribute("roles", hasSize(2)));
    }

    @Test
    void find() throws Exception {
        UUID id = UUID.randomUUID();

        UserDto user = new UserDto();
        user.setId(id);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@gmail.com");
        user.setRoleId(UUID.randomUUID());
        user.setPassword("password");
        user.setUserDetails("userDetails");

        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());

        when(service.findByParameters(any(UserDto.class))).thenReturn(List.of(user));
        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(post("/users/find")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "email@gmail.com")
                        .param("roleId", UUID.randomUUID().toString())
                        .flashAttr("userDto", new UserDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/find"))
                .andExpect(model().attribute("roles", hasSize(2)))
                .andExpect(model().attribute("users", hasSize(1)));

    }
}