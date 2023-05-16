package homework8Gradle.homework8Gradle.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "products")
@Entity
@Setter
@Getter
public class Product {
    @Id
    UUID id;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Column(name = "price", nullable = false)
    Long price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manufacturer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Manufacturer manufacturer;

}
