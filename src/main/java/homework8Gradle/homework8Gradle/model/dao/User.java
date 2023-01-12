package homework8Gradle.homework8Gradle.model.dao;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table (name = "\"users\"")
@Entity
@Setter
@Getter
public class User {
    @Id
    String id;

    String email;
    String password;
    String firstName;
    String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Role> roles = new HashSet<>();

}
