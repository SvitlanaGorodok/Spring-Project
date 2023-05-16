package homework8Gradle.homework8Gradle.model.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Table(name = "manufacturers")
@Entity
@Setter
@Getter
public class Manufacturer {

    @Id
    UUID id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="manufacturer")
    Set<Product> products;

}
