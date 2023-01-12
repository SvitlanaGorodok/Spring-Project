package homework8Gradle.homework8Gradle;

import homework8Gradle.homework8Gradle.model.dao.Role;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DBInit {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void dbinit() {
        User admin = new User();
        admin.setId(UUID.randomUUID().toString());
        admin.setEmail("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setFirstName("admin");
        admin.setLastName("admin");
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName("ROLE_ADMIN");
        admin.setRoles(Set.of(role));
        userRepository.save(admin);
    }
}

