package homework8Gradle.homework8Gradle.model.dao;
import homework8Gradle.homework8Gradle.model.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table (name = "\"user\"")
@Entity
@Data
public class User {
    @Id
    String id;

    String email;
    String password;
    String firstName;
    String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<UserRole> roles;

}
