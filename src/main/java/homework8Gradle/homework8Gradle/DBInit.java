package homework8Gradle.homework8Gradle;

import homework8Gradle.homework8Gradle.model.UserRole;
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
        admin.setPassword(encoder.encode("adminpass"));
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setRoles(Set.of(UserRole.ROLE_ADMIN));
        userRepository.save(admin);
    }
}

