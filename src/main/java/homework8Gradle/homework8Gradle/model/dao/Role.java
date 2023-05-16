package homework8Gradle.homework8Gradle.model.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "roles")
@Entity
@Setter
@Getter
public class Role {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    Set<User> users = new HashSet<>();
}
