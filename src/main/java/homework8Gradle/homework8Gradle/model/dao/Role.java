package homework8Gradle.homework8Gradle.model.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Entity
@Setter
@Getter
public class Role {
    @Id
    private String id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    Set<User> users = new HashSet<>();
}
