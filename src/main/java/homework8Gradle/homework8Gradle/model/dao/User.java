package homework8Gradle.homework8Gradle.model.dao;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Table (name = "\"users\"")
@Entity
@Setter
@Getter
public class User {

    @Id
    UUID id;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    String email;

    @Column(name = "first_name", nullable = false, length = 50)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;

    @Column(name = "password", nullable = false, length = 100)
    String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Role role;

}
